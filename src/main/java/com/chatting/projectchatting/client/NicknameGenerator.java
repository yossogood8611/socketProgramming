package com.chatting.projectchatting.client;

import static java.lang.Math.random;

public class NicknameGenerator {

    private static final String[] FIRST_NAME = {
            "화기애애", "화난", "장난스런", "찡그린", "유쾌한", "긴장한", "초보자", "신입"
    };

    private static final String[] SECOND_NAME = {
            "무지", "제이지", "어피치", "춘식", "네오", "튜브"
    };

    public static String randomNicknameGenerate(){
        int firstIdx = randomValueGenerate(FIRST_NAME.length);
        int secondIdx = randomValueGenerate(SECOND_NAME.length);
        return FIRST_NAME[firstIdx] + " " + SECOND_NAME[secondIdx];
    }

    private static int randomValueGenerate(int maxRange){
        return (int) (random() * maxRange);
    }
}