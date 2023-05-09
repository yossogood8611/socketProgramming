package com.chatting.projectchatting.server;

import javafx.scene.text.Text;

public class CurrentUserCounter extends Thread {

    private int count;
    private final Text text;

    public CurrentUserCounter(Text text){
        this.text = text;
        this.count = 0;
    }

    @Override
    public void run() {
        while (true) {
            text.setText("현재 " + count + "명 접속중..");
        }
    }

    public synchronized void increase(){
        count = count + 1;
    }

    public synchronized void decrease(){
        count = count - 1;
    }
}
