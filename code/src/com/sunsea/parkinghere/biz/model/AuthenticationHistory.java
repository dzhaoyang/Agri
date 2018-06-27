package com.sunsea.parkinghere.biz.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AuthenticationHistories")
public class AuthenticationHistory extends Identity {
    
    private String osFamily;
    
    private String browser;
    
    private String userAgent;
    
    @Indexed
    @DBRef
    private User user;
    
    private String username;
    
    @Indexed
    private Date loginAt;
    
    private String authType;
    
    private String ipAddress;
   
    
    public String getOsFamily() {
        return osFamily;
    }
    
    public void setOsFamily(String osFamily) {
        this.osFamily = osFamily;
    }
    
    public String getBrowser() {
        return browser;
    }
    
    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public Date getLoginAt() {
        return loginAt;
    }
    
    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            setUsername(user.getUsername());
        } else {
            setUsername(null);
        }
    }
    
    public String getAuthType() {
        return authType;
    }
    
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
