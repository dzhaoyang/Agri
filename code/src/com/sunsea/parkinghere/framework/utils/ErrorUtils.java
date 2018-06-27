package com.sunsea.parkinghere.framework.utils;

public class ErrorUtils {
    
    public static String toString(Exception e) {
        StringBuffer result = new StringBuffer();
        for (StackTraceElement element : e.getStackTrace()) {
            result.append(element.toString()).append("\n");
        }
        return result.toString();
    }
}
