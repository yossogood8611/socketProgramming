package com.chatting.projectchatting;

import com.chatting.projectchatting.server.ConnectThread;
import java.io.IOException;
import java.net.ServerSocket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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

        //-----------------------------------------------------
        Button btn1 = new Button("서버 오픈");

        btn1.setOnAction(event -> {
            try {
                ConnectThread connectThread = new ConnectThread(new ServerSocket());
                connectThread.init("localhost", 5001);
                connectThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        root.getChildren().addAll(btn1);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("서버");
        stage.show();
    }



}
