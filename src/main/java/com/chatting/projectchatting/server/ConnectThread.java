package com.chatting.projectchatting.server;

import com.chatting.projectchatting.domain.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConnectThread extends Thread {

    private final List<ChatUser> connects = new LinkedList<>();
    private final ServerSocket ss;
    private final CurrentUserCounter currentUserCounter;

    public ConnectThread(ServerSocket ss, CurrentUserCounter currentUserCounter) {
        this.ss = ss;
        this.currentUserCounter = currentUserCounter;
    }

    public void init(String hostname, int port) {
        try {
            ss.bind(new InetSocketAddress(hostname, port));
            System.out.println("binding");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            currentUserCounter.start();
            while (true){
                Socket socket = ss.accept();
                if (socket.isConnected()){
                    ClientThread clientThread = new ClientThread(socket, this);
                    clientThread.start();
                    new CurrentUserThread(this).start();
                    connects.add(new ChatUser(null, clientThread));
                    currentUserCounter.increase();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveAll(Message message){
        for (ChatUser user : connects) {
            System.out.println("receiveAll ------------- " +user.getClientThread().getId());
            user.getClientThread().receive(message);
        }
    }

    public void receiveCurrentUsers() {
        List<String> currentUsers = connects.stream()
                .filter(ChatUser::isOk)
                .map(ChatUser::getUsername)
                .collect(Collectors.toList());
        receiveAll(Message.current(currentUsers));
    }

    public void setUserName(String userName, Socket socket) {
        for (ChatUser user : connects) {
            if (user.getClientThread().isSame(socket)) {
                user.setUsername(userName);
            }
        }
    }


    public CurrentUserCounter getCurrentUserCounter() {
        return currentUserCounter;
    }

    public void disconnectSocket(Socket socket) {
        for (ChatUser user : connects) {
            if(user.getClientThread().isSame(socket)){
                connects.remove(user);
            }
        }
    }

    private Optional<ClientThread> findSocketByUserName(String userName) {
        return connects.stream()
                .filter(user -> user.isSameUserName(userName))
                .map(ChatUser::getClientThread)
                .findFirst();
    }

    public void dropOutUser(Message message) {
        findSocketByUserName(message.getText())
                .ifPresent((clientThread) -> {
                    clientThread.receive(Message.out(message.getSender(), message.getText()));
                    currentUserCounter.decrease();
                    disconnectSocket(clientThread.getSocket());
                    receiveAll(Message.text(message.getSender(), "님이 [" +  message.getText() + "]님을 강퇴하였습니다."));
                });
    }
}
