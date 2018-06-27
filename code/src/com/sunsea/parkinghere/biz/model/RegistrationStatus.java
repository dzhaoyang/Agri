package com.sunsea.parkinghere.biz.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = EnumSerializer.class)
public enum RegistrationStatus {
	
	PENDING("等待"),
    APPROVED("通过"),
    DENIED("拒绝");
    
    private RegistrationStatus(String label) {
        this.label = label;
    }
    
    private String label;
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
