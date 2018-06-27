package com.sunsea.parkinghere.biz.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;

public abstract class Identity implements StringIdentity {
    
    @Id
    private String id;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = StringUtils.isEmpty(id) ? null : id;
    }
    
}
