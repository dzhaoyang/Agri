package com.sunsea.parkinghere.biz.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PasswordChangeRequests")
public class PasswordChangeRequest {
    
    @Id
    private String id;
    
    @DBRef
    private User user;
    
    @Indexed
    private String requestId;
    
    private Date requestTime;
    
    private Date expiryTime;
    
    private Boolean used = Boolean.FALSE;
    
    public PasswordChangeRequest() {
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public Date getRequestTime() {
        return requestTime;
    }
    
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
    
    public Date getExpiryTime() {
        return expiryTime;
    }
    
    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
    
    public Boolean getUsed() {
        return used;
    }
    
    public void setUsed(Boolean used) {
        this.used = used;
    }
    
}
