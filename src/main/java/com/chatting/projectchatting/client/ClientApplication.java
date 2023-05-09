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


public class ClientApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    private Client client = null;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        HBox subRoot = new HBox();
        HBox textRoot = new HBox();
        root.setPrefSize(400, 300);
        root.setSpacing(10);
        subRoot.setSpacing(10);
        textRoot.setSpacing(10);

        //-----------------------------------------------------
        Button btn1 = new Button("접속");
        Button btn2 = new Button("데이터 전송");
        Button btn3 = new Button("완료");
        Text text = new Text("닉네임");
        TextArea textArea = new TextArea();
        TextField senderField = new TextField();
        TextField textField = new TextField();
        btn2.setDisable(true);

        btn1.setOnAction(event -> {
            client = new Client(btn1, btn2, textArea);
            client.start();
        });

        btn2.setOnAction(event -> client.send(senderField.getText(), textField.getText()));
        btn3.setOnAction(event -> {
            senderField.setDisable(true);
            btn3.setDisable(true);
        });

        subRoot.getChildren().addAll(btn1, text, senderField, btn3);
        textRoot.getChildren().addAll(textField, btn2);
        root.getChildren().addAll(subRoot, textArea, textRoot);
        //-----------------------------------------------------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("클라이언트");
        stage.show();
    }
}
