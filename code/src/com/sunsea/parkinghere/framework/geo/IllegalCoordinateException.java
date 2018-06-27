package com.sunsea.parkinghere.framework.geo;

public class IllegalCoordinateException extends RuntimeException {
    
    public IllegalCoordinateException() {
        super();
    }
    
    public IllegalCoordinateException(String message,
                                      Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public IllegalCoordinateException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public IllegalCoordinateException(String message) {
        super(message);
    }
    
    public IllegalCoordinateException(Throwable cause) {
        super(cause);
    }
    
}
