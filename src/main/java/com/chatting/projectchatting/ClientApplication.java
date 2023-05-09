package com.chatting.projectchatting;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.chatting.projectchatting.client.Receiver;
import com.chatting.projectchatting.client.Sender;
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
    private Sender sender;
    private Scanner sc;

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
                socket.connect(new InetSocketAddress("192.168.0.75", 5001));
                sender = new Sender(socket);
                sc = new Scanner(System.in);
                Receiver receiver = new Receiver(socket);
                receiver.start();
                btn1.setDisable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn2.setOnAction(event -> {
            sender.send(textField.getText());
        });

        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = textField.getText();
                sender.send(textField.getText());
            }
        });


        root.getChildren().addAll(btn1, btn2, textField);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("서버");
        stage.show();
    }
}
