package com.linktreeclone.api.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.linktreeclone.api.payload.response.ApiResponse;
import com.linktreeclone.api.payload.response.UserResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<UserResponse>> handleNotFoundException(
        NotFoundException e,
        WebRequest request
    ) throws Exception {
        try {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                "404",
                e.getMessage(),
                e.getDetails()
            );
            return new ResponseEntity<ApiResponse<UserResponse>>(
                new ApiResponse<UserResponse>(
                    null, 
                    errorResponse
                ), HttpStatus.OK
            );
        } catch (Exception ex) {
            throw ex;
        }
    }

    @ExceptionHandler(CredentialsTakenException.class)
    public ResponseEntity<ApiResponse<UserResponse>> handleCredentialsTakenException(
        CredentialsTakenException e,
        WebRequest request
    ) throws Exception {
        try {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                "400",
                e.getMessage(),
                e.getDetails()
            );
            return new ResponseEntity<ApiResponse<UserResponse>>(
                new ApiResponse<UserResponse>(
                    null, 
                    errorResponse
                ), HttpStatus.OK
            );
        } catch (Exception ex) {
            throw ex;
        }
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<UserResponse>> handleMissingRequestHeaderException(
        MissingRequestHeaderException e,
        WebRequest request
    ) throws Exception {
        try {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                "400",
                e.getMessage(),
                e.getBody().getDetail()
            );
            return new ResponseEntity<ApiResponse<UserResponse>>(
                new ApiResponse<UserResponse>(
                    null, 
                    errorResponse
                ), HttpStatus.OK
            );
        } catch (Exception ex) {
            throw ex;
        }
    }
}
