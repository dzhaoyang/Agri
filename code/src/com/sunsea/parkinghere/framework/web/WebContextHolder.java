package com.sunsea.parkinghere.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContextHolder {
    
    private static final ThreadLocal<HttpServletRequest> httpServletRequestHolder = new ThreadLocal<HttpServletRequest>();
    
    private static final ThreadLocal<HttpServletResponse> httpServletResponseHolder = new ThreadLocal<HttpServletResponse>();
    
    public static HttpServletRequest currentHttpServletRequest() {
        return httpServletRequestHolder.get();
    }
    
    public static HttpServletResponse currentHttpServletResponse() {
        return httpServletResponseHolder.get();
    }
    
    public static void currentHttpServletRequest(HttpServletRequest request) {
        httpServletRequestHolder.set(request);
    }
    
    public static void currentHttpServletResponse(HttpServletResponse response) {
        httpServletResponseHolder.set(response);
    }
    
    public static void clear() {
        httpServletRequestHolder.set(null);
        httpServletResponseHolder.set(null);
    }
    
}
