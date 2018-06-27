package com.sunsea.parkinghere.framework.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    
    /**
     * @param response
     * @param name
     * @param expiry
     *            days
     * @param value
     */
    public static void saveCookie(HttpServletResponse response,
                                  String name,
                                  int expiry,
                                  String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        int cookieAage = 60 * 60 * 24 * expiry;
        cookie.setMaxAge(cookieAage);
        response.addCookie(cookie);
    }
    
    /**
     * @param response
     * @param name
     * @param expiry
     *            days
     * @param value
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
}
