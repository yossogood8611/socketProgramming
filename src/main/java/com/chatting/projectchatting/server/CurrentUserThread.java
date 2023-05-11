package com.chatting.projectchatting.server;

public class CurrentUserThread extends Thread {

    private final ConnectThread connectThread;

    public CurrentUserThread(ConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(3000);
                connectThread.receiveCurrentUsers();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
