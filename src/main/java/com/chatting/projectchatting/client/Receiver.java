package com.chatting.projectchatting.client;

import com.chatting.projectchatting.domain.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

public class Receiver extends Thread{

    private final Socket socket;
    public Receiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    Message message = (Message) inputStream.readObject();
                    if (Objects.nonNull(message)) {
                        System.out.println(message);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
