package com.linktreeclone.api.payload.response;

import com.linktreeclone.api.exception.ApiErrorResponse;

public class ApiResponse<T> {
    
    private T user;

    private ApiErrorResponse error;

    public ApiResponse() {}

    public ApiResponse(T user, ApiErrorResponse error) {
        this.user = user;
        this.error = error;
    } 

    public T getUser() {
        return this.user;
    }

    public ApiErrorResponse getError() {
        return this.error;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public void setError(ApiErrorResponse error) {
        this.error = error;
    }
}
