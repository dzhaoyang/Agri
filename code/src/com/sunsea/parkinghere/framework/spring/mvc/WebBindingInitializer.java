package com.sunsea.parkinghere.framework.spring.mvc;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

public class WebBindingInitializer implements
                                  org.springframework.web.bind.support.WebBindingInitializer {
    
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new DateEditor(true));
    }
    
}
