package com.linktreeclone.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CredentialsTakenException extends CustomException {

    public CredentialsTakenException() {
        super();
    }

    public CredentialsTakenException(String message, String details) {
        super(message, details);
    }
}
