package com.sunsea.parkinghere.framework.edm;

public interface SecurityEventAroundListener extends SecurityEventListener {
    
    public void beforeFire(SecurityEventObject eventObject);
    
    public void afterFire(SecurityEventObject eventObject);
    
}
