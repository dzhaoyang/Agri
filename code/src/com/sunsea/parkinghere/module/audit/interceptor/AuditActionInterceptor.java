package com.sunsea.parkinghere.module.audit.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;
import com.sunsea.parkinghere.module.audit.model.AuditEntry;

@Aspect
public class AuditActionInterceptor {
    
    private static final Log logger = LogFactory.getLog(AuditActionInterceptor.class);
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Around("@within(com.sunsea.parkinghere.module.audit.annotation.Auditable)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        AuditEntryTrack track = AuditEntryContexts.getCurrent();
        if (track == null) {
            return joinPoint.proceed();
        }
        
        track.setMatched(true);
        
        AuditEntry auditEntry = track.getAuditEntry();
        
        Object target = joinPoint.getTarget();
        
        Auditable classAuditMetatadata = target.getClass()
                                               .getAnnotation(Auditable.class);
        
        MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
        Method method = methodSign.getMethod();
        
        try {
            int requestBodyParamIndex = -1;
            Annotation[][] annos = method.getParameterAnnotations();
            for (int i = 0; i < annos.length; i++) {
                for (int j = 0; j < annos[i].length; j++) {
                    Annotation anno = annos[i][j];
                    if (anno instanceof RequestBody) {
                        requestBodyParamIndex = i;
                    }
                }
            }
            
            if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
                MethodInvocationProceedingJoinPoint mipj = (MethodInvocationProceedingJoinPoint) joinPoint;
                if (requestBodyParamIndex >= 0) {
                    Object requestBody = mipj.getArgs()[requestBodyParamIndex];
                    if (requestBody != null) {
                        auditEntry.setRequestedParameters(objectMapper.writeValueAsString(requestBody));
                    }
                }
            }
        }
        catch (Exception e) {
            
        }
        
        Auditable methodAuditMetadata = method.getAnnotation(Auditable.class);
        if (methodAuditMetadata != null) {
            if (StringUtils.isNotEmpty(methodAuditMetadata.category())) {
                auditEntry.setCategory(methodAuditMetadata.category());
            }
            
            if (StringUtils.isNotEmpty(methodAuditMetadata.value())) {
                auditEntry.setActionName(methodAuditMetadata.value());
            }
        }
        else {
            auditEntry.setActionName(method.getName());
        }
        
        if (auditEntry.getCategory() == null) {
            if (StringUtils.isNotEmpty(classAuditMetatadata.category())) {
                auditEntry.setCategory(classAuditMetatadata.category());
            }
        }
        
        if (auditEntry.getCategory() == null) {
            auditEntry.setCategory(target.getClass().getPackage().getName());
        }
        
        if (classAuditMetatadata != null) {
            if (StringUtils.isNotEmpty(classAuditMetatadata.value())) {
                auditEntry.setServiceName(classAuditMetatadata.value());
            }
            else {
                auditEntry.setServiceName(target.getClass().getName());
            }
        }
        
        auditEntry.setPackageName(target.getClass().getPackage().getName());
        auditEntry.setClassName(target.getClass().getName());
        auditEntry.setMethodName(method.getName());
        
        Object princpal = SecurityContextHolder.getContext()
                                               .getAuthentication()
                                               .getPrincipal();
        if (princpal != null && princpal instanceof UserDetails) {
            UserDetails user = (UserDetails) princpal;
            auditEntry.setRequestBy(user.getUsername());
        }
        
        boolean responseJsonEnabled = method.isAnnotationPresent(ResponseBody.class);
        auditEntry.setRequestType(responseJsonEnabled ? "json" : "html");
        
        try {
            Object result = joinPoint.proceed();
            if (responseJsonEnabled && result != null && result instanceof Map) {
                if (WebUtils.isFailedMap((Map) result)) {
                    auditEntry.setStatus("failed");
                    if (!StringUtils.isEmpty(WebUtils.getFailedMessage((Map) result))) {
                        auditEntry.setErrorMessage(WebUtils.getFailedMessage((Map) result));
                    }
                    else if (null != WebUtils.getFailedMessages((Map) result)) {
                        auditEntry.setErrorMessage(StringUtils.join(WebUtils.getFailedMessages((Map) result),
                                                                    ".\n"));
                    }
                    else {
                        auditEntry.setErrorMessage("Error message not found.");
                    }
                    return result;
                }
            }
            auditEntry.setStatus("succeed");
            return result;
        }
        catch (Throwable e) {
            logger.error(e, e);
            auditEntry.setStatus("failed");
            // auditEntry.setErrorDetail(ex.printStackTrace(new String));
            auditEntry.setErrorMessage(e.getMessage());
            auditEntry.setErrorDetail(getExceptionDetail(e));
            if (responseJsonEnabled) {
                return WebUtils.failedMap(e.getMessage());
            }
            throw e;
        }
    }
    
    public String getExceptionDetail(Throwable e) {
        try {
            StringWriter sWriter = new StringWriter();
            // create PrintWriter for StringWriter
            PrintWriter pWriter = new PrintWriter(sWriter);
            // now print the stacktrace to PrintWriter we just created
            e.printStackTrace(pWriter);
            return sWriter.toString();
        }
        catch (Exception ex) {
            return "";
        }
        finally {
        }
    }
    
}
