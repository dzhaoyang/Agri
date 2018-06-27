package com.sunsea.parkinghere.framework.email.template;

public class EmailTemplateException extends RuntimeException {
    
    public EmailTemplateException() {
        super();
    }
    
    public EmailTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public EmailTemplateException(String message) {
        super(message);
    }
    
    public EmailTemplateException(Throwable cause) {
        super(cause);
    }
    
}
