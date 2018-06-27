package com.sunsea.parkinghere.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class IdentityUtils {
    
    private static MessageDigest md5MessageDigest;
    
    static {
        try {
            md5MessageDigest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    public static String generateHexIdentity(String name) {
        try {
            if (md5MessageDigest != null) {
                return new String(Hex.encodeHex(md5MessageDigest.digest(name.getBytes("utf-8"))));
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        String test = "fdjksa  fdjslaf";
        System.out.println(generateHexIdentity(test));
    }
    
}
