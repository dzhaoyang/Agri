package com.sunsea.parkinghere.framework.spring.mvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class MappingExceptionResolver extends SimpleMappingExceptionResolver {
    
    private static final Log logger = LogFactory.getLog(MappingExceptionResolver.class);
    
    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        try {
            super.logException(ex, request);
        }
        finally {
            logger.error(ex, ex);
        }
    }
    
}
