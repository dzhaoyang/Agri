package com.sunsea.parkinghere.module.carowner.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AbstractController {
    private static final Log logger = LogFactory.getLog(AbstractController.class);
    
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        logger.error("CarOwnerApp Error", e);
        return "app/carowner/error";
    }
    
}
