package com.linktreeclone.api.payload.response;

import com.linktreeclone.api.exception.ApiErrorResponse;

public class ApiResponse<T> {
    
    private T data;

    private ApiErrorResponse error;

    public ApiResponse() {}

    public ApiResponse(T data, ApiErrorResponse error) {
        this.data = data;
        this.error = error;
    } 

    public T getData() {
        return this.data;
    }

    public ApiErrorResponse getError() {
        return this.error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setError(ApiErrorResponse error) {
        this.error = error;
    }
}
