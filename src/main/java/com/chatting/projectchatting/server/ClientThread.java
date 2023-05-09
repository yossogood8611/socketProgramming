package com.chatting.projectchatting.server;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.chatting.projectchatting.ServerApplication;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ClientThread extends Thread {
    private Socket socket;
    private ConnectThread connectThread;
    public ClientThread(Socket socket,  ConnectThread connectThread) {
        this.socket = socket;
        this.connectThread = connectThread;
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()){
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String str = dataInputStream.readUTF();
                System.out.println(str);
                connectThread.receiveAll(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive(String str) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            dataOutputStream.writeUTF(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
