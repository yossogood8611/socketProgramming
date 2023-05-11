package com.chatting.projectchatting.server;

import com.chatting.projectchatting.domain.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveAll(Message message){
        for (ChatUser user : connects) {
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
        connects.remove(socket);
    }
}
