package com.linktreeclone.api.payload.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class MultipleErrorResponse extends ErrorResponse {
    private List<FieldError> errors;

    public MultipleErrorResponse() {
        super();
        this.errors = new ArrayList<>();
    }

    public MultipleErrorResponse(
        HttpStatus status,
        String errorCode,
        String message,
        String details,
        List<FieldError> errors
    ) {
        super(status, errorCode, message, details);
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return this.errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }
}
