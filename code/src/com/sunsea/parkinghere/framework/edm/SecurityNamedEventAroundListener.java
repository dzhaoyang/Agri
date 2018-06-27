package com.sunsea.parkinghere.framework.edm;

public interface SecurityNamedEventAroundListener extends
                                                 SecurityNamedEventListener {
    
    public void beforeFire(SecurityNamedEventObject eventObject);
    
    public void afterFire(SecurityNamedEventObject eventObject);
    
}
