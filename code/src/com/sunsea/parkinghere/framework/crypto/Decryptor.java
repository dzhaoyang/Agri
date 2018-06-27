package com.sunsea.parkinghere.framework.crypto;

public interface Decryptor {
    
    public String decrypt(byte[] encrypted, String key, String salt) throws Exception;
    
}
