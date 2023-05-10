package com.chatting.projectchatting.client;

import com.chatting.projectchatting.domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

public class Receiver extends Thread {

    private final Socket socket;
    private final Client client;
    private boolean out = false;

    public Receiver(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    System.out.println(out);
                    System.out.println(inputStream);

                    if (out)
                        break;

                    Message message = (Message) inputStream.readObject();
                    if (Objects.nonNull(message)) {
                        client.receiveMessage(message);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.out = true;
    }
}
