package com.sunsea.parkinghere.exception;

/**
 * 
 */
public class EntityException extends RuntimeException {
    
    public EntityException() {
        super();
    }
    
    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public EntityException(String message) {
        super(message);
    }
    
    public EntityException(Throwable cause) {
        super(cause);
    }
    
}
