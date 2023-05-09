package com.chatting.projectchatting.client;

import com.chatting.projectchatting.domain.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javafx.scene.control.Button;

public class Client extends Thread{

    public static final String HOST_NAME = "localhost";
    public static final int PORT = 5001;

    private Sender sender;
    private Receiver receiver;
    private final Button button1;
    private final Button button2;

    public Client(Button button1, Button button2) {
        this.sender = null;
        this.receiver = null;
        this.button1 = button1;
        this.button2 = button2;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(HOST_NAME, PORT));
            sender = new Sender(socket);
            receiver = new Receiver(socket);
            receiver.start();
            button1.setDisable(true);
            button2.setDisable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String sender, String text) {
        this.sender.send(Message.text(sender, text));
    }

    public Sender getSender() {
        return sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
