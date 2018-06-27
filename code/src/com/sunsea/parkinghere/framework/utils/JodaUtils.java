package com.sunsea.parkinghere.framework.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class JodaUtils {
    
    public static final DateFormat SHORT_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    
    public static DateTime convert(Date date) {
        return (date == null) ? null : new DateTime(date);
    }
    
    public static Date convert(DateTime dateTime) {
        return (dateTime != null) ? dateTime.toDate() : null;
    }
    
    public static String toShortString(Date date) {
        if (date == null) {
            return "";
        }
        return SHORT_FORMAT.format(date);
    }
    
}
