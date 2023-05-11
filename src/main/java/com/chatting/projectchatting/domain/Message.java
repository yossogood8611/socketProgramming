package com.chatting.projectchatting.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Message implements Serializable {
    private static final int MAX_LENGTH = 255;

    private final MessageType type;
    private final String sender;
    private final String text;
    private final List<String> currentUsers;
    private final LocalDateTime date;


    public Message(MessageType type, String sender, String text, List<String> currentUsers, LocalDateTime date) {
        validateTextLength(text);
        this.type = type;
        this.sender = sender;
        this.text = text;
        this.currentUsers = currentUsers;
        this.date = date;
    }

    public static Message text(String sender, String text) {
        return new Message(MessageType.CHAT, sender, text, null,LocalDateTime.now());
    }

    public static Message firstMessage(String sender) {
        return new Message(MessageType.ROOM_IN, sender,"님이 채팅방에 들어왔습니다.", null, LocalDateTime.now());
    }

    public static Message outMessage() {
        return new Message(MessageType.CHAT, "운영자", "누군가 채팅방에서 나갔습니다.",  null,LocalDateTime.now());
    }

    public static Message event(String sender, String text) {
        return new Message(MessageType.Event, sender, text, null,LocalDateTime.now());
    }

    public static Message current(List<String> currentUsers){
        return new Message(MessageType.ROOM_USER, null, "", currentUsers, LocalDateTime.now());
    }

    public static Message logout(String sender){
        return new Message(MessageType.LOG_OUT, sender,"님이 채팅방에 나갔습니다.", null, LocalDateTime.now());
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

    private void validateTextLength(String text) {
        if (text.length() > MAX_LENGTH) {
            throw new RuntimeException();
        }
    }

    public List<String> getCurrentUsers() {
        return currentUsers;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.date.format(formatter) + ") " + this.sender + " : " + this.text ;
    }

}
