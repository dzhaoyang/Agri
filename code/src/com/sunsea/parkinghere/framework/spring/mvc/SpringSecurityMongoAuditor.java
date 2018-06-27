package com.sunsea.parkinghere.framework.spring.mvc;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityMongoAuditor implements AuditorAware<String> {
    
    public String getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        else {
            return SecurityContextHolder.getContext()
                                        .getAuthentication()
                                        .getName();
        }
    }
    
}
