package com.chatting.projectchatting.server;

import com.chatting.projectchatting.domain.Message;
import com.chatting.projectchatting.domain.MessageType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ClientThread extends Thread {
    private final Socket socket;
    private final ConnectThread connectThread;
    public ClientThread(Socket socket,  ConnectThread connectThread) {
        this.socket = socket;
        this.connectThread = connectThread;
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()){
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) inputStream.readObject();

                connectThread.receiveAll(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive(Message message) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
