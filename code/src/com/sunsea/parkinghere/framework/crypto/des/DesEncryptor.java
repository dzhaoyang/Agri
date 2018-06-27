package com.sunsea.parkinghere.framework.crypto.des;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sunsea.parkinghere.framework.crypto.Encryptor;

public class DesEncryptor implements Encryptor {
    
    public byte[] encrypt(String text, String key, String salt) throws Exception {
        String plainKey = key + salt;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Key = md5.digest(plainKey.getBytes("UTF-8"));
        
        DESKeySpec dks = new DESKeySpec(md5Key);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(text.getBytes("UTF-8"));
    }
    
}
