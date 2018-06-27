package com.sunsea.parkinghere.module.carowner.model;

import java.io.Serializable;

public class CarOwnerNonceContext implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String nonce;
    
    private String username;
    
    private double userLocationX = -1;
    
    private double userLocationY = -1;
    
    private String userLocationLabel;
    
    private double locationX = -1;
    
    private double locationY = -1;
    
    private String locationLabel;
    
    private long createAt = System.currentTimeMillis();
    
    private long modifyAt = System.currentTimeMillis();
    
    public String getNonce() {
        return nonce;
    }
    
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
    
    public double getUserLocationX() {
        return userLocationX;
    }
    
    public void setUserLocationX(double locationX) {
        this.userLocationX = locationX;
    }
    
    public double getUserLocationY() {
        return userLocationY;
    }
    
    public void setUserLocationY(double locationY) {
        this.userLocationY = locationY;
    }
    
    public String getUserLocationLabel() {
        return userLocationLabel;
    }
    
    public void setUserLocationLabel(String locationLabel) {
        this.userLocationLabel = locationLabel;
    }
    
    public long getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public long getModifyAt() {
        return modifyAt;
    }
    
    public void setModifyAt(long modifyAt) {
        this.modifyAt = modifyAt;
    }
    
    public double getLocationX() {
        return locationX < 0 ? getUserLocationX() : locationX;
    }
    
    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }
    
    public double getLocationY() {
        return locationY < 0 ? getUserLocationY() : locationY;
    }
    
    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }
    
    public String getLocationLabel() {
        return locationLabel == null ? getUserLocationLabel() : locationLabel;
    }
    
    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }
}
