package com.chatting.projectchatting.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Sender {

    private final Socket socket;
    private final DataOutputStream outputStream;

    public Sender(Socket socket) {
        try {
            this.socket = socket;
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String text){
        try {
            outputStream.writeUTF(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
