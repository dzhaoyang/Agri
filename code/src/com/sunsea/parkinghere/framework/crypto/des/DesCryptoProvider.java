package com.sunsea.parkinghere.framework.crypto.des;

import com.sunsea.parkinghere.framework.crypto.CryptoProvider;
import com.sunsea.parkinghere.framework.crypto.Decryptor;
import com.sunsea.parkinghere.framework.crypto.Encryptor;

public class DesCryptoProvider implements CryptoProvider {
    
    private Encryptor encryptor = new DesEncryptor();
    
    private Decryptor decryptor = new DesDecryptor();
    
    public String getAlgorithm() {
        return "DES";
    }
    
    public Encryptor getEncryptor() {
        return encryptor;
    }
    
    public Decryptor getDecryptor() {
        return decryptor;
    }
    
}
