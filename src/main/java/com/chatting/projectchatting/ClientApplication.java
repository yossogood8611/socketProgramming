package com.chatting.projectchatting;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.chatting.projectchatting.client.Receiver;
import com.chatting.projectchatting.client.Sender;
import com.chatting.projectchatting.domain.Message;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ClientApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    private Socket socket = null;
    private Sender sender = null;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setPrefSize(400, 300);
        root.setSpacing(10);

        //-----------------------------------------------------
        Button btn1 = new Button("접속");
        Button btn2 = new Button("데이터 전송");
        TextField textField = new TextField();

        btn1.setOnAction(event -> {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("localhost", 5001));
                sender = new Sender(socket);
                Receiver receiver = new Receiver(socket);
                receiver.start();
                btn1.setDisable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn2.setOnAction(event -> sender.send(Message.text("우진", textField.getText())));
        root.getChildren().addAll(btn1, btn2, textField);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("클라이언트");
        stage.show();
    }
}
