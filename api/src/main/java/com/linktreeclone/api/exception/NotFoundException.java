package com.linktreeclone.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends CustomException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message, String details) {
        super(message, details);
    }
}
