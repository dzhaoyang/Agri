package com.sunsea.parkinghere.framework.edm.akka;

import com.sunsea.parkinghere.framework.edm.SecurityMessage;

public class DefaultSecurityMessage implements SecurityMessage {
    
    private String path;
    
    private Object message;
    
    public DefaultSecurityMessage(String path, Object message) {
        this.path = path;
        this.message = message;
    }
    
    public String getPath() {
        return path;
    }
    
    public Object getMessage() {
        return message;
    }
    
}
