package com.sunsea.parkinghere.framework.utils;

import java.security.SecureRandom;
import java.util.Random;

public class KeyUtils {
    
    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    
    private static Random random = new SecureRandom();
    
    public static String generateKey() {
        byte[] verifierBytes = new byte[6];
        random.nextBytes(verifierBytes);
        return getAuthorizationCodeString(verifierBytes);
    }
    
    public static String generateKey(int length) {
        if (length <= 1) {
            throw new IllegalArgumentException("The length of key must be greater than zero!");
        }
        byte[] verifierBytes = new byte[length];
        random.nextBytes(verifierBytes);
        String codeString = getAuthorizationCodeString(verifierBytes);
        return codeString.toLowerCase();
    }
    
    protected static String getAuthorizationCodeString(byte[] verifierBytes) {
        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(KeyUtils.generateKey());
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(KeyUtils.generateKey(10));
        }
    }
    
}
