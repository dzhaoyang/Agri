package com.sunsea.parkinghere.framework.web;

public class MIME {
    
    public static final String CONTENT_TYPE_PNG = "image/png";
    
    public static final String CONTENT_TYPE_JPG = "image/jpeg";
    
    public static final String CONTENT_TYPE_BMP = "image/bmp";
    
    public static final String CONTENT_TYPE_JSON = "application/json";
    
    public static boolean isPng(String contentType) {
        return "image/png".equals(contentType) || "image/x-png".equals(contentType);
    }
    
    public static boolean isJpeg(String contentType) {
        return "image/pjpeg".equals(contentType) || "image/jpeg".equals(contentType);
    }
    
    public static boolean isBmp(String contentType) {
        return "image/bmp".equals(contentType);
    }
    
    public static boolean isJson(String contentType) {
        return "application/json".equals(contentType) || "application/x-json".equals(contentType);
    }
    
}
