package com.chatting.projectchatting.server;

import javafx.scene.control.TextField;

public class UserCounter extends Thread {

    private final TextField field;
    private Integer count;

    public UserCounter(TextField field) {
        this.field = field;
        count = 0;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                field.setText(count + "명");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void increase(){
        this.count = count + 1;
    }
}
