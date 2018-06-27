package com.sunsea.parkinghere.framework.edm;

public interface SecurityEventListener {
    
    public boolean isSupported(SecurityEventObject eventObject);
    
    public void onFire(SecurityEventObject eventObject);
    
}
