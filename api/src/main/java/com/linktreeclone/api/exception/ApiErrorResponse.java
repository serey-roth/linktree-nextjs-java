package com.linktreeclone.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import jakarta.validation.constraints.NotBlank;

public class ApiErrorResponse {

    //http status code
    private HttpStatus status;

    // in case we want to provide API based custom error code
    private String errorCode;

    // customer error message to the client API
    @NotBlank(message = "Message is mandatory!")
    private String message;

    // Any furthur details which can help client API
    private String details;

    // Time of the error.make sure to define a standard time zone to avoid any confusion
    private LocalDateTime timestamp;

    public ApiErrorResponse() {}

    public ApiErrorResponse(
        HttpStatus status,
        String errorCode,
        String message,
        String details
    ) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getHttpStatus() {
        return this.status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetails() {
        return this.details;
    }

    public String getTimestamp() {
        return this.timestamp.toString();
    }

    public void setHttpStatus(HttpStatus status) {
        this.status = status;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

