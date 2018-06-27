package com.sunsea.parkinghere.exception;

public class ConstraintViolationException extends RuntimeException {
    
    public ConstraintViolationException() {
        super();
    }
    
    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ConstraintViolationException(String message) {
        super(message);
    }
    
    public ConstraintViolationException(Throwable cause) {
        super(cause);
    }
    
}
