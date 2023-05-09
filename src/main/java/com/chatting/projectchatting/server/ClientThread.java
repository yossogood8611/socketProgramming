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
                connectThread.receiveAll(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
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


}
