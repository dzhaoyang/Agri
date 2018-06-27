package com.sunsea.parkinghere.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    
    private static Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[a-zA-Z0-9\\-\\.]+\\.[(a-zA-z)]{2,4}$");
    
    public static boolean isValidEmail(String str) {
        Matcher regMatcher = regexPattern.matcher(str);
        return regMatcher.matches();
    }
    
}
