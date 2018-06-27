package com.sunsea.parkinghere.exception;

public class DaoException extends RuntimeException {
    
    public DaoException() {
        super();
    }
    
    public DaoException(String message) {
        super(message);
    }
    
    public DaoException(Throwable cause) {
        super(cause);
    }
    
    public DaoException(String message, Throwable ex) {
        super(message, ex);
    }
    
}
