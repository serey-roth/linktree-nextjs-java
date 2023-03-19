package com.linktreeclone.api.payload.output;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class FieldErrorsList extends Error {
    private List<FieldError> errors;

    public FieldErrorsList() {
        super();
        this.errors = new ArrayList<>();
    }

    public FieldErrorsList(
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
