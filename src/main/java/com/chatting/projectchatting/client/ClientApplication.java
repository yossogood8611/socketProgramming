package com.chatting.projectchatting.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import java.io.*;
import java.util.function.Function;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
public class ClientApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    private Client client = null;

    @Override
    public void start(Stage stage) throws Exception {
        TabPane tabPane = new TabPane();

        //-----------------------------------------------------
        // Tab 1: 방 선택
        Tab tab1 = new Tab("방 선택");
        VBox tab1Root = new VBox();
        Button btnRefrsh = new Button("새로고침");
        Button btn1 = new Button("접속");
        Button btn2 = new Button("데이터 전송");
    
        Text text = new Text("닉네임");
        TextArea textArea = new TextArea();
        TextField senderField = new TextField();

        HBox subRoot = new HBox();
        ListView<String> roomList = new ListView<>();
        ObservableList<String> rooms = FXCollections.observableArrayList();
        this.setRoom(roomList, rooms);
        tab1Root.setPadding(new Insets(10, 10, 10, 10));
        tab1Root.setSpacing(10);

        subRoot.getChildren().addAll( text, senderField,btn1);
        tab1Root.getChildren().addAll(subRoot,btnRefrsh,roomList);

        tab1.setContent(tab1Root);

        //-----------------------------------------------------
        // Tab 2: 채팅방
        Tab tab2 = new Tab("채팅방");
        VBox tab2Root = new VBox();

        HBox textRoot = new HBox();
        tab2Root.setPrefSize(600, 500);
        tab2Root.setSpacing(10);
        tab2Root.setPadding(new Insets(10, 0, 0, 0));
        subRoot.setSpacing(10);
        textRoot.setSpacing(10);

        TextField textField = new TextField();
        textArea.setEditable(false);
        btn2.setDisable(true);
        tab2Root.setSpacing(10);

        textRoot.getChildren().addAll(textField, btn2);
        tab2Root.getChildren().addAll( textArea, textRoot);

        tab2.setContent(tab2Root);
        //-----------------------------------------------------

        //-----------------------------------------------------
        // Tab 3: 설정
        Tab tab3 = new Tab("설정");
        VBox tab3Root = new VBox();
        tab3Root.setPadding(new Insets(10, 10, 10, 10));
        tab3Root.setSpacing(10);

        // Add settings logic and UI elements here.

        tab3.setContent(tab3Root);
        //----------------------------------------------------

        // 탭을 닫을 수 없게 설정
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // 탭 추가
        tabPane.getTabs().addAll(tab1, tab2, tab3);

        Scene scene = new Scene(tabPane);
        stage.setScene(scene);
        stage.setTitle("클라이언트");
        stage.show();


        // 이벤트 리스너
        
        // 접속 이벤트 
        btn1.setOnAction(event -> {
            int port = Integer.valueOf(roomList.getSelectionModel().getSelectedItem()); 
            String nick = senderField.getText();
             
             if (nick.isEmpty()) {
                 senderField.requestFocus();
             } else {
                 client = new Client(btn1, btn2, textArea, port);
                 client.start();
                 tabPane.getSelectionModel().select(1);
                 textField.requestFocus();
                 stage.setTitle("방 번호:" + port);
             }
         });

         // 텍스트 전송
         btn2.setOnAction(event -> {
            client.send(senderField.getText(), textField.getText());
            textField.setText("");
        });

        // 새로고침 이벤트
        btnRefrsh.setOnAction(event -> {
            this.setRoom(roomList, rooms);
       });
    }

    /**
     * 방 세팅
     * @param roomList
     * @param rooms
     */
    private void setRoom(ListView<String> roomList, ObservableList<String> rooms){
        try {
            Reader r = new FileReader("server.txt");
            BufferedReader br = new BufferedReader(r);
            rooms.clear();
             // 한 줄씩 읽기
            String line;
            while ((line = br.readLine()) != null) {
                // 읽은 한 줄 출력
                rooms.add(line);
            }
            roomList.setItems(rooms);
            br.close();
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
