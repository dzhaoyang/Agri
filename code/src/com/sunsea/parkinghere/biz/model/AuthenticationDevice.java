package com.sunsea.parkinghere.biz.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AuthenticationDevices")
public class AuthenticationDevice extends Identity {
    
    @DBRef
    private User user;
    
    private String osFamily;
    
    private String deviceId;
    
    private String token;
    
    private String previousToken;
    
    private Date expireDate;
    
    @CreatedDate
    private Date createAt;
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getOsFamily() {
        return osFamily;
    }
    
    public void setOsFamily(String osFamily) {
        this.osFamily = osFamily;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getPreviousToken() {
        return previousToken;
    }
    
    public void setPreviousToken(String previousToken) {
        this.previousToken = previousToken;
    }
    
    public Date getExpireDate() {
        return expireDate;
    }
    
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
}
