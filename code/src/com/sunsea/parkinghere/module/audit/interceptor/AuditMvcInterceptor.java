package com.sunsea.parkinghere.module.audit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sunsea.parkinghere.module.audit.model.AuditEntry;
import com.sunsea.parkinghere.module.audit.repository.AuditEntryRepository;

public class AuditMvcInterceptor implements HandlerInterceptor {
    
    @Autowired
    private AuditEntryRepository auditEntryRepository;
    
    public AuditMvcInterceptor() {
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (AuditEntryContexts.getCurrent() == null) {
            AuditEntryTrack track = AuditEntryTrack.create();
            AuditEntryContexts.setCurrent(track);
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        if (AuditEntryContexts.getCurrent() == null) {
            return;
        }
        AuditEntryTrack track = AuditEntryContexts.getCurrent();
        if (!track.isMatched()) {
            return;
        }
        try {
            AuditEntry auditEntry = track.getAuditEntry();
            auditEntry.setRequestedUrl(request.getRequestURL().toString());
            auditEntry.setHttpMethod(request.getMethod());
            
            // handle the client real ip address
            // since the proxy between the client and backend server, the real
            // ip is hidden in http header
            String clientAddress = request.getHeader("X-Forwarded-For");
            if (StringUtils.isEmpty(clientAddress)) {
                clientAddress = request.getRemoteAddr();
            }
            auditEntry.setClientAddress(clientAddress);
            auditEntry.setDuration(System.currentTimeMillis() - track.getLastMills());
            if (StringUtils.isNotEmpty(auditEntry.getRequestBy()) || !"null".equals(auditEntry.getRequestBy())) {
                doAudit(auditEntry);
            }
        }
        finally {
            AuditEntryContexts.clear();
        }
    }
    
    public void doAudit(final AuditEntry auditEntry) {
        auditEntryRepository.save(auditEntry);
    }
    
}
