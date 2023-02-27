package com.linktreeclone.api.payload.response;

public class ApiResponse<T> {
    
    private T data;

    private ErrorResponse error;

    public ApiResponse() {}

    public ApiResponse(T data, ErrorResponse error) {
        this.data = data;
        this.error = error;
    } 

    public T getData() {
        return this.data;
    }

    public ErrorResponse getError() {
        return this.error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}
