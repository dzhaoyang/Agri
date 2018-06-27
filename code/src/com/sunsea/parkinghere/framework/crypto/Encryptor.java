package com.sunsea.parkinghere.framework.crypto;

public interface Encryptor {
    
    public byte[] encrypt(String text, String key, String salt) throws Exception;
    
}
