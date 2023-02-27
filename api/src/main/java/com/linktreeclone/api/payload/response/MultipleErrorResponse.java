package com.linktreeclone.api.payload.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class MultipleErrorResponse extends ErrorResponse {
    private List<String> errors;

    public MultipleErrorResponse() {
        super();
        this.errors = new ArrayList<>();
    }

    public MultipleErrorResponse(
        HttpStatus status,
        String errorCode,
        String message,
        String details,
        List<String> errors
    ) {
        super(status, errorCode, message, details);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
