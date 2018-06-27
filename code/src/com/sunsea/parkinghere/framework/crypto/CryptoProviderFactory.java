package com.sunsea.parkinghere.framework.crypto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sunsea.parkinghere.Configuration;
import com.sunsea.parkinghere.framework.crypto.des.DesCryptoProvider;

public class CryptoProviderFactory {
    
    private static final Log logger = LogFactory.getLog(CryptoProviderFactory.class);
    
    static CryptoProvider instance;
    
    static {
        String providerClassName = Configuration.getInstance()
                                                .getProperty(CryptoProvider.class.getName(),
                                                             DesCryptoProvider.class.getName());
        try {
            Class providerClazz = Class.forName(providerClassName);
            instance = (CryptoProvider) providerClazz.newInstance();
        }
        catch (Exception e) {
            logger.error(e, e);
        }
    }
    
    public static CryptoProvider getProvider() {
        return instance;
    }
    
}
