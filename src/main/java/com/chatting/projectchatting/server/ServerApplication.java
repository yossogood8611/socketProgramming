package com.chatting.projectchatting.server;

import java.io.IOException;
import java.net.ServerSocket;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ServerApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setPrefSize(400, 300);
        root.setSpacing(10);
        root.setPadding(new Insets( 10, 0, 0, 0));
        //-----------------------------------------------------
        Button btn1 = new Button("서버 오픈");
        Text text = new Text("현재" + 0 + "명 접속중...");

        btn1.setOnAction(event -> {
            try {
                ConnectThread connectThread = new ConnectThread(new ServerSocket(), new CurrentUserCounter(text));
                connectThread.init("localhost", 5001);
                connectThread.start();
                btn1.setDisable(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        root.getChildren().addAll(btn1, text);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("서버");
        stage.show();
    }
}
