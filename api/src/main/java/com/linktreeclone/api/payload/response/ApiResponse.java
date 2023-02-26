package com.linktreeclone.api.payload.response;

import com.linktreeclone.api.exception.ApiErrorResponse;

public class ApiResponse {
    
    private JwtResponse user;

    private ApiErrorResponse error;

    public ApiResponse() {}

    public ApiResponse(JwtResponse user, ApiErrorResponse error) {
        this.user = user;
        this.error = error;
    } 

    public JwtResponse getUser() {
        return this.user;
    }

    public ApiErrorResponse getError() {
        return this.error;
    }

    public void setUser(JwtResponse user) {
        this.user = user;
    }

    public void setError(ApiErrorResponse error) {
        this.error = error;
    }
}
