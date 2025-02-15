package com.BubbleWrap.SearchEmptySeat.utils;

import java.security.SecureRandom;

public class RandomPassWord {
    private static final String CHARATERS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final int PASSWORD_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generateTemporaryPassword(){
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++){
            password.append(CHARATERS.charAt(random.nextInt(CHARATERS.length())));
        }
        return  password.toString();
    }
}
