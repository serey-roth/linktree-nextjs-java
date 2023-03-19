package com.linktreeclone.api.payload.output;

public class ApiResult<T> {
    
    private T data;

    private Error error;

    public ApiResult() {}

    public ApiResult(T data, Error error) {
        this.data = data;
        this.error = error;
    } 

    public T getData() {
        return this.data;
    }

    public Error getError() {
        return this.error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
