package com.sunsea.parkinghere.biz.repository.parameter;

import java.util.Date;

import com.sunsea.parkinghere.module.audit.openapi.AbstractQueryParameter;

public class AuthenticationHistoryQueryParameter extends AbstractQueryParameter {
    
    private String username;
    
    private String authType;// password or token or form
    
    private String ipAddress;
    
    private Date loginAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

}
