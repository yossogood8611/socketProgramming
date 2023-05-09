package com.chatting.projectchatting.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomService {
    private final Map<Integer, ChatRoom> roomMap = new HashMap<>();
    private static final ChatRoomService instance = new ChatRoomService();
    private ChatRoomService() {
    }
    public static ChatRoomService getInstance() {
        return instance;
    }

    public void createRoom(String title, ClientThread clientThread) {
        roomMap.put(roomMap.size(), new ChatRoom(title, List.of(clientThread)));
    }

    public void roomIn(Integer roomId, ClientThread clientThread){
        if (roomMap.containsKey(roomId)) {
            roomMap.get(roomId).addUser(clientThread);
        }
    }

    public List<ClientThread> getRoomUser(Integer roomId) {
        return roomMap.containsKey(roomId) ?
                roomMap.get(roomId).getClientThreads() : Collections.emptyList();
    }

    public List<ChatRoom> getAllChatRoom(){
        return (List<ChatRoom>) roomMap.values();
    }
}
