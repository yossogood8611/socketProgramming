package com.chatting.projectchatting;

import com.chatting.projectchatting.client.Client;
import com.chatting.projectchatting.client.Receiver;
import com.chatting.projectchatting.client.Sender;
import com.chatting.projectchatting.domain.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ClientApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    private Client client = null;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setPrefSize(400, 300);
        root.setSpacing(10);

        //-----------------------------------------------------
        Button btn1 = new Button("접속");
        Button btn2 = new Button("데이터 전송");
        btn2.setDisable(true);
        TextField senderField = new TextField();
        TextField textField = new TextField();

        btn1.setOnAction(event -> {
            client = new Client(btn1, btn2);
            client.start();
        });

        btn2.setOnAction(event -> client.send(senderField.getText(), textField.getText()));
        root.getChildren().addAll(btn1, btn2, senderField, textField);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("클라이언트");
        stage.show();
    }
}
