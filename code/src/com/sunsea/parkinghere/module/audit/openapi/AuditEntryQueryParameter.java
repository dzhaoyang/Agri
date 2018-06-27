package com.sunsea.parkinghere.module.audit.openapi;

import java.util.Date;

public class AuditEntryQueryParameter extends AbstractQueryParameter {
    
    private String requestBy;
    
    private String clientAddress;
    
    private String requestedUrl;
    
    private String status;
    
    private String className;
    
    private Date requestAt;
    
    public String getRequestBy() {
        return requestBy;
    }
    
    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getClientAddress() {
        return clientAddress;
    }
    
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
    
    public String getRequestedUrl() {
        return requestedUrl;
    }
    
    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getRequestAt() {
        return requestAt;
    }
    
    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }
    
}
