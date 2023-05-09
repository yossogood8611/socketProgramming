package com.chatting.projectchatting.server;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConnectThread extends Thread {

    private List<ClientThread> connects = new LinkedList<>();
    private ServerSocket ss;

    public ConnectThread(ServerSocket ss) {
        this.ss = ss;
    }

    public void init(String hostname, int port) {
        try {
            ss.bind(new InetSocketAddress(hostname, port));
            System.out.println("binding");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                Socket socket = ss.accept();
                ClientThread clientThread = new ClientThread(socket, this);
                connects.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveAll(String str){
        for (ClientThread connect : connects) {
            connect.receive(str);
        }
    }
}
