package com.chatting.projectchatting.server;

import java.io.Serializable;
import java.util.List;

public class ChatRoom implements Serializable {
    private final String title;
    private final List<ClientThread> clientThreads;

    public ChatRoom(String title, List<ClientThread> clientThreads) {
        this.title = title;
        this.clientThreads = clientThreads;
    }

    public void addUser(ClientThread clientThread) {
        clientThreads.add(clientThread);
    }

    public String getTitle() {
        return title;
    }

    public List<ClientThread> getClientThreads() {
        return clientThreads;
    }
}
