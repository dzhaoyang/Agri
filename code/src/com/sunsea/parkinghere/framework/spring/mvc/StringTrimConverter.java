package com.sunsea.parkinghere.framework.spring.mvc;

import org.springframework.core.convert.converter.Converter;

public class StringTrimConverter implements Converter<String, String> {
    
    @Override
    public String convert(String source) {
        if (source != null) {
            return source.trim();
        }
        return source;
    }
    
}
