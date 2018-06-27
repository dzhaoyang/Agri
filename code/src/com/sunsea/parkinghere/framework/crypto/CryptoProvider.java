package com.sunsea.parkinghere.framework.crypto;

public interface CryptoProvider {
    
    public String getAlgorithm();
    
    public Encryptor getEncryptor();
    
    public Decryptor getDecryptor();
    
}
