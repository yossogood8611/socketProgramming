package com.chatting.projectchatting.client;

public class ProfanityFilter {

    private static final String[] badWords = {"시발", "병신", "새끼", "썅", "쌍년", "씨발", "지랄", "씹"};
    private static final String mask = "**";

    public static String filter(String input) {
        String result = input;
        for (String badWord : badWords) {
            result = result.replaceAll(badWord, mask);
        }
        return result;
    }
}

