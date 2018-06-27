package com.sunsea.parkinghere.exception;

public class BizServiceException extends RuntimeException {
    
    public BizServiceException() {
        super();
    }
    
    public BizServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BizServiceException(String message) {
        super(message);
    }
    
    public BizServiceException(Throwable cause) {
        super(cause);
    }
    
}
