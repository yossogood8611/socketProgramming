package com.chatting.projectchatting.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread{

    private final Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            while (true) {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String response = dataInputStream.readUTF();
                if (!response.equals("")) {
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
