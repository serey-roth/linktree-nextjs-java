package com.linktreeclone.api.exception;

public class CustomException extends RuntimeException {
    
    private String details; 

    public CustomException() {
        super();
    }

    public CustomException(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
