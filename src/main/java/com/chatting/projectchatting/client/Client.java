package com.chatting.projectchatting.client;

import com.chatting.projectchatting.domain.Message;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class Client extends Thread{

    public static final String HOST_NAME = "localhost";

    private Sender sender;
    private Receiver receiver;
    private final Button button1;
    private final Button button2;
    private final TextArea textArea;
    private final int port;

    private final String userName;

    private final ListView<String> userList;
    public Client(Button button1,
                  Button button2,
                  TextArea textArea,
                  int port,
                  String userName,
                  ListView<String> userList) {
        this.sender = null;
        this.receiver = null;
        this.button1 = button1;
        this.button2 = button2;
        this.textArea = textArea;
        this.port = port;
        this.userName = userName;
        this.userList = userList;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(HOST_NAME, port));
            sender = new Sender(socket);
            receiver = new Receiver(socket, this);
            send(Message.firstMessage(userName));
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

    public void send(Message message) {
        this.sender.send(message);
    }

    public void receiveMessage(Message message) {
        String getText = message.toString();
        textArea.appendText(ProfanityFilter.filter(getText) +"\n");
    }


    public void setCurrentUser(List<String> currentUser) {
        Platform.runLater(() -> {
            userList.getItems().clear();
            for (String user : currentUser) {
                userList.getItems().add(user);
            }
        });
    }

    public void close(){
        System.out.println("Close --- ");
        this.sender.send(Message.logout());
    }

    public void out(Message message){
        Platform.runLater(() -> {
            this.userList.getItems().clear();
            this.textArea.setText(message.getSender() + "에 의해 강퇴되었습니다.");
        });
    }
}
