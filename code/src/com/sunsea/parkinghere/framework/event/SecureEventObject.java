package com.sunsea.parkinghere.framework.event;

import java.util.EventObject;

public class SecureEventObject extends EventObject {
    
    public static final long serialVersionUID = 0L;
    
    public SecureEventObject(Object source) {
        super(source);
    }
    
}
