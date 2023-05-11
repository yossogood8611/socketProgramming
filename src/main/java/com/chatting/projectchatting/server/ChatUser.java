package com.chatting.projectchatting.server;

import java.util.Objects;

public class ChatUser {
    private String username;
    private final ClientThread clientThread;

    public ChatUser(String username, ClientThread clientThread) {
        this.username = username;
        this.clientThread = clientThread;
    }

    public boolean isOk(){
        return Objects.nonNull(username);
    }

    public String getUsername() {
        return username;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
