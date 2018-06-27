package com.sunsea.parkinghere.framework.spring.mvc;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateEditor extends PropertyEditorSupport {
    
    private final boolean allowEmpty;
    
    public DateEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
    
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? this.getLongDateFormat().format(value) : "");
    }
    
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        }
        else {
            try {
                setValue(this.getLongDateFormat().parse(text));
            }
            catch (ParseException ex) {
                try {
                    setValue(this.getShortDateFormat().parse(text));
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
