package com.sunsea.parkinghere.exception;

public class InvalidAttachmentContentException extends RuntimeException {
    
    public InvalidAttachmentContentException() {
        super();
    }
    
    public InvalidAttachmentContentException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidAttachmentContentException(String message) {
        super(message);
    }
    
    public InvalidAttachmentContentException(Throwable cause) {
        super(cause);
    }
    
}
