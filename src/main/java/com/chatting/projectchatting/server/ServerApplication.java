package com.chatting.projectchatting.server;

import java.io.IOException;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ServerApplication extends Application {

    static int port = 5000; 
    static StringBuffer sb = new StringBuffer("");
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
        Button btn1 = new Button("방 오픈");
        ListView<String> roomList = new ListView<>();
        ObservableList<String> rooms = FXCollections.observableArrayList();

        btn1.setOnAction(event -> setEvent(roomList, rooms));
        root.getChildren().addAll(btn1, roomList);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("서버");
        stage.show();
    }

    private void setEvent(ListView<String> roomList, ObservableList<String> rooms) {
        try {
            String roomId = "" + port;
            ServerRoom serverRoom = new ServerRoom(port);
            serverRoom.init("localhost", port);
            rooms.add(roomId);
            sb.append(roomId + "\n");
            roomList.setItems(rooms);

            try {
                Writer writer = new FileWriter("server.txt");
                writer.write(sb.toString());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            port++;
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
