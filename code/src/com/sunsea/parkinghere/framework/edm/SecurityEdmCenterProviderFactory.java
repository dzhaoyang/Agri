package com.sunsea.parkinghere.framework.edm;

import java.util.ServiceLoader;

public class SecurityEdmCenterProviderFactory {
    
    static class SecurityEdmCenterProviderFactoryHolder {
        
        static SecurityEdmCenterProvider instance;
        
        static {
            ServiceLoader<SecurityEdmCenterProvider> serviceLoader = ServiceLoader.load(SecurityEdmCenterProvider.class);
            for (SecurityEdmCenterProvider provider : serviceLoader) {
                instance = provider;
            }
        }
        
    }
    
    public static SecurityEdmCenterProvider getProvider() {
        return SecurityEdmCenterProviderFactoryHolder.instance;
    }
    
}
