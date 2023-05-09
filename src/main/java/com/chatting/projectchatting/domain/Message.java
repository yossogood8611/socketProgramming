package com.chatting.projectchatting.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private final MessageType type;
    private final String sender;
    private final String text;
    private final LocalDateTime date;

    public Message(MessageType type, String sender, String text, LocalDateTime date) {
        this.type = type;
        this.sender = sender;
        this.text = text;
        this.date = date;
    }

    public static Message text(String sender, String text) {
        return new Message(MessageType.CHAT, sender, text, LocalDateTime.now());
    }

    public static Message event(String sender, String text) {
        return new Message(MessageType.Event, sender, text, LocalDateTime.now());
    }

    public MessageType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "{" + type.toString() + " " + this.sender + " " + this.text + " " + this.date + "}";
    }

}
