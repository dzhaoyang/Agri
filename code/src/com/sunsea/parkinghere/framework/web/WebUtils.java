package com.sunsea.parkinghere.framework.web;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class WebUtils {
    
    public static Map<String, Object> succeedMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("succeed", Boolean.TRUE);
        return result;
    }
    
    public static Map<String, Object> succeedMap(Object obj) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("succeed", Boolean.TRUE);
        if (null != obj) {
            result.put("data", obj);
        }
        return result;
    }
    
    public static Map<String, Object> failedMap() {
        return failedMap("");
    }
    
    public static boolean isFailedMap(Map<String, Object> map) {
        return (map.get("succeed") == Boolean.FALSE);
    }
    
    public static String getFailedMessage(Map<String, Object> map) {
        return (String) map.get("message");
    }
    
    public static String[] getFailedMessages(Map<String, Object> map) {
        Object messages = map.get("messages");
        if (messages == null) {
            return null;
        }
        if (messages instanceof String[]) {
            return (String[]) messages;
        }
        else if (messages instanceof List) {
            return ((List<String>) map.get("messages")).toArray(new String[0]);
        }
        else {
            return null;
        }
    }
    
    public static Map<String, Object> failedMap(String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("succeed", Boolean.FALSE);
        result.put("message", message);
        return result;
    }
    
    public static Map<String, Object> failedMap(String[] messages) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("succeed", Boolean.FALSE);
        result.put("messages", messages);
        return result;
    }
    
    public static Map<String, Object> failedMap(List<String> messages) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("succeed", Boolean.FALSE);
        result.put("messages", messages);
        return result;
    }
    
    public static Map<String, Object> failedMap(String message,
                                                String code,
                                                String field) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("succeed", Boolean.FALSE);
        result.put("message", message);
        Map<String, Object> errorDetail = new HashMap<String, Object>();
        errorDetail.put("code", code);
        errorDetail.put("field", field);
        result.put("error", errorDetail);
        return result;
    }
    
    public static String escapeHTML(String content) {
        return content.replace("<", "&lt;").replace(">", "&gt;");
    }
    
    public static String unescapeHTML(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        return content.replace("&lt;", "<").replace("&gt;", ">");
    }
    
    public static ResponseEntity<Map> buildResponseEntity(Exception exception) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application",
                                            "json",
                                            Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        
        return new ResponseEntity<Map>(failedMap(exception.getMessage()),
                                       headers,
                                       HttpStatus.valueOf(200));
    }
    
    public static Map<String, String> parseQueryString(String query) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        try {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                result.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                           URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }
        catch (Exception e) {
        }
        return result;
    }
    
    public static Map<String, String> getRequestParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String[]> map = request.getParameterMap();
        for (String key : map.keySet()) {
            String[] values = map.get(key);
            if (values != null && values.length > 0) {
                parameters.put(key, values[0]);
            }
        }
        return parameters;
    }
    
    public static String getUri(String fullUrl) {
        if (StringUtils.isBlank(fullUrl)) {
            return "";
        }
        int startIndex = fullUrl.indexOf("://");
        if (startIndex < 0) {
            return "";
        }
        
        startIndex = fullUrl.indexOf("/", startIndex + 3);
        if (startIndex < 0) {
            return "";
        }
        
        return fullUrl.substring(startIndex);
    }
}
