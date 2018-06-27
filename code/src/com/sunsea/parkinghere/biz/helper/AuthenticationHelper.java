package com.sunsea.parkinghere.biz.helper;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sunsea.parkinghere.biz.model.AuthenticationDevice;
import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.model.User;

import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

public class AuthenticationHelper {
    
    private static final Log logger = LogFactory.getLog(AuthenticationHelper.class);
    
    private static UASparser parser;
    
    static {
        try {
            parser = new UASparser(UASparser.class.getClassLoader()
                                                  .getResourceAsStream("user_agent_strings.txt"));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    static UserAgentInfo parse(String userAgentString) {
        try {
            return parser.parse(userAgentString);
        }
        catch (IOException e) {
            return null;
        }
    }
    
    static String getSummary(String userAgentString) {
        try {
            UserAgentInfo uai = parser.parse(userAgentString);
            return uai.getOsName() + " - " + uai.getUaName();
        }
        catch (IOException e) {
            logger.error(e, e);
            return "Unknown";
        }
    }
    
    static String getOsFamily(String userAgentString) {
        try {
            UserAgentInfo uai = parser.parse(userAgentString);
            return uai.getOsName();
        }
        catch (IOException e) {
            logger.error(e, e);
            return "Unknown";
        }
    }
    
    static String getBrowserFamily(String userAgentString) {
        try {
            UserAgentInfo uai = parser.parse(userAgentString);
            return uai.getUaName();
        }
        catch (IOException e) {
            logger.error(e, e);
            return "Unknown";
        }
    }
    
    public static AuthenticationHistory authenticationHistoryFromHttpRequest(HttpServletRequest request) {
        AuthenticationHistory authenticationHistory = new AuthenticationHistory();
        String userAgent = request.getHeader("User-Agent");
        authenticationHistory.setOsFamily(getOsFamily(userAgent));
        authenticationHistory.setBrowser(getBrowserFamily(userAgent));
        authenticationHistory.setUserAgent(userAgent);
        try {
        	User user = (User) SecurityContextHolder.getContext()
		                                                    .getAuthentication()
		                                                    .getPrincipal();
            authenticationHistory.setUser(user);
        }
        catch (Exception e) {
        }
        authenticationHistory.setLoginAt(new Date());
        authenticationHistory.setIpAddress(request.getRemoteAddr());// todo:add
                                                                    // X-Forwarded-For
        return authenticationHistory;
    }
    
    public static AuthenticationDevice authenticationDeviceFromHttpRequest(HttpServletRequest request) {
        AuthenticationDevice device = new AuthenticationDevice();
        String userAgent = request.getHeader("User-Agent");
        device.setOsFamily(AuthenticationHelper.getOsFamily(userAgent));
        device.setDeviceId(request.getParameter("j_deviceId"));
        try {
        	User user = (User) SecurityContextHolder.getContext()
		                                                    .getAuthentication()
		                                                    .getPrincipal();
            device.setUser(user);
        }
        catch (Exception e) {
        }
        device.setCreateAt(new Date());
        return device;
    }
    
}
