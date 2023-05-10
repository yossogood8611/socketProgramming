package com.chatting.projectchatting.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import javafx.scene.text.Text;

public class ServerRoom {
    private ServerSocket serverSocket;

    public ServerRoom(int roomId) {
    }

    public void init(String host, int port) throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(host, port));
        Text text = new Text("현재" + 0 + "명 접속중...");
        new ConnectThread(serverSocket, new CurrentUserCounter(text)).start();
    }
}
