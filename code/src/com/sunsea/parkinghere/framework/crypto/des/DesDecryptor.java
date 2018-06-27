package com.sunsea.parkinghere.framework.crypto.des;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sunsea.parkinghere.framework.crypto.Decryptor;

public class DesDecryptor implements Decryptor {
    
    public String decrypt(byte[] encrypted, String key, String salt) throws Exception {
        String plainKey = key + salt;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Key = md5.digest(plainKey.getBytes("UTF-8"));
        
        DESKeySpec dks = new DESKeySpec(md5Key);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, "UTF-8");
    }
    
}
