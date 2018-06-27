package com.sunsea.parkinghere.biz.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

public abstract class BaseEntity<T> extends Identity {
    
    @Indexed
    private String name;
    
    private String description;
    
    @CreatedDate
    private Date createAt;
    
    @LastModifiedDate
    private Date modifiedAt;
    
    public BaseEntity() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            name = name.trim();
        }
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date created) {
        this.createAt = created;
    }
    
    public Date getModifiedAt() {
        return modifiedAt;
    }
    
    public void setModifiedAt(Date modified) {
        this.modifiedAt = modified;
    }
    
}
