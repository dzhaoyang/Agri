package com.sunsea.parkinghere.module.audit.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Notifications")
public class Notification {
    
    @Id
    private String id;
    
    private long notifyTo;
    
    private String category;
    
    private String properties;
    
    private String message;
    
    private long createBy;
    
    private String creatorName;
    
    private Date createAt = new Date();
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public long getNotifyTo() {
        return notifyTo;
    }
    
    public void setNotifyTo(long notifyTo) {
        this.notifyTo = notifyTo;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getProperties() {
        return properties;
    }
    
    public void setProperties(String properties) {
        this.properties = properties;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public long getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public String getCreatorName() {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
}
