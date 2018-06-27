package com.sunsea.parkinghere.framework.spring.mvc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class DateTimeConverter implements Converter<String, Date> {
    
    private boolean allowEmpty;
    
    public DateTimeConverter() {
        this.allowEmpty = true;
    }
    
    public DateTimeConverter(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
    
    public Date convert(String text) {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            return null;
        }
        else {
            try {
                return this.getLongDateFormat().parse(text);
            }
            catch (ParseException ex) {
                try {
                    return this.getShortDateFormat().parse(text);
                }
                catch (ParseException ex1) {
                    IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex1.getMessage());
                    iae.initCause(ex1);
                    throw iae;
                }
            }
        }
    }
    
    private DateFormat getShortDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
    
    private DateFormat getLongDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
}
