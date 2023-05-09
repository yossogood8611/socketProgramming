package com.chatting.projectchatting.client;

import com.chatting.projectchatting.domain.Message;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {

    private final Socket socket;
    private final ObjectOutputStream outputStream;

    public Sender(Socket socket) {
        try {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message){
        try {
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
