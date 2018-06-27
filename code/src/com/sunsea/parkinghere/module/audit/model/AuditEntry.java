package com.sunsea.parkinghere.module.audit.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AuditEntries")
public class AuditEntry {
    
    @Id
    private String id;
    
    private String requestBy;
    
    private String packageName;
    
    private String className;
    
    private String methodName;
    
    private String category;
    
    private String serviceName;
    
    private String actionName;
    
    private String requestedUrl;
    
    private String requestedParameters;
    
    private Date requestAt = new Date();
    
    private String clientAddress;
    
    private long duration;
    
    private String requestType; // .do .json
    
    private String status;
    
    private String errorMessage;
    
    private String errorDetail;
    
    private String params;
    
    private String httpMethod;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        if (StringUtils.isEmpty(id)) {
            id = null;
        }
        this.id = id;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getActionName() {
        return actionName;
    }
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    public String getRequestedUrl() {
        return requestedUrl;
    }
    
    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }
    
    public String getRequestedParameters() {
        return requestedParameters;
    }
    
    public void setRequestedParameters(String requestedParameters) {
        this.requestedParameters = requestedParameters;
    }
    
    public Date getRequestAt() {
        return requestAt;
    }
    
    public void setRequestAt(Date created) {
        this.requestAt = created;
    }
    
    public String getRequestBy() {
        return requestBy;
    }
    
    public void setRequestBy(String username) {
        this.requestBy = username;
    }
    
    public String getClientAddress() {
        return clientAddress;
    }
    
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public String getRequestType() {
        return requestType;
    }
    
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getErrorDetail() {
        return errorDetail;
    }
    
    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
    
    public String getParams() {
        return params;
    }
    
    public void setParams(String params) {
        this.params = params;
    }
    
    public String getHttpMethod() {
        return httpMethod;
    }
    
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
    
}
