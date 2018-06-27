package com.sunsea.parkinghere.framework.edm;

public class SecurityNamedEventObject extends SecurityEventObject {
    
    private String name;
    
    public SecurityNamedEventObject(String name, Object source) {
        super(source);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
