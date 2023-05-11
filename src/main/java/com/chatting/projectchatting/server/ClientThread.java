package com.chatting.projectchatting.server;

import com.chatting.projectchatting.domain.Message;
import com.chatting.projectchatting.domain.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;


public class ClientThread extends Thread {
    private final Socket socket;
    private final ConnectThread connectThread;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientThread(Socket socket, ConnectThread connectThread) {
        this.socket = socket;
        this.connectThread = connectThread;
        this.inputStream = null;
        this.outputStream = null;
    }

    public void run() {
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            while (!Thread.currentThread().isInterrupted()) {
                Message message = (Message) inputStream.readObject();
                switch (message.getType()) {
                    case ROOM_IN:
                        connectThread.setUserName(message.getSender(), socket);
                        break;
                    case LOG_OUT:
                        System.out.println("LOGOUT  ----- ");
                        this.disconnect(Message.outMessage());
                        continue;
                    case OUT:
                        connectThread.dropOutUser(message);
                        continue;
                    default: break;

                }

                connectThread.receiveAll(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(Message.outMessage());
        }
    }

    public void receive(Message message)  {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void disconnect(Message message) {
        connectThread.receiveAll(message);
        connectThread.getCurrentUserCounter().decrease();
        connectThread.disconnectSocket(socket);
    }

    public boolean isSame(Socket socket) {
        return this.socket.equals(socket);
    }

    public Socket getSocket() {
        return socket;
    }
}
