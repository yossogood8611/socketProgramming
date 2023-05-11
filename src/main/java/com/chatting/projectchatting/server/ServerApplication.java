package com.chatting.projectchatting.server;

import java.io.IOException;

import java.io.*;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;

public class ServerApplication extends Application {

    static int port = 5000;
    static StringBuffer sb = new StringBuffer("");
    private static HashSet<String> roomNameList = new HashSet<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        HBox subRoot = new HBox();
        subRoot.setSpacing(10);
        root.setPrefSize(400, 300);
        root.setSpacing(10);
        root.setPadding(new Insets( 10, 0, 0, 0));

        //-----------------------------------------------------
        Button btn1 = new Button("방 개설");
        TextField txtRoomName = new TextField();

        ListView<String> roomList = new ListView<>();
        ObservableList<String> rooms = FXCollections.observableArrayList();

        txtRoomName.setOnAction(actionEvent -> {
            String str = txtRoomName.getText();
            int count = roomNameList.size();
            roomNameList.add(str);
            boolean dupChk = (count != roomNameList.size());

            if(!str.isEmpty() && dupChk){
                setRoomEvent(roomList, rooms, str);
                txtRoomName.setText("");
            } else{
                txtRoomName.requestFocus();
            }
        });

        btn1.setOnAction(event -> {
            String str = txtRoomName.getText();
            int count = roomNameList.size();
            roomNameList.add(str);
            boolean dupChk = (count != roomNameList.size());

            if(!str.isEmpty() && dupChk){
                setRoomEvent(roomList, rooms, str);
                txtRoomName.setText("");
            } else{
                txtRoomName.requestFocus();
            }
        });

        subRoot.getChildren().addAll(txtRoomName, btn1);
        root.getChildren().addAll(subRoot, roomList);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("서버");
        stage.show();
    }
    private void setRoomEvent(ListView<String> roomList, ObservableList<String> rooms,String str) {
        try {
            ServerRoom serverRoom = new ServerRoom();
            serverRoom.init("localhost", port);
            roomNameList.add(str);
            rooms.add(str);
            sb.append(port + "|" + str + "\n");
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
