package com.linktreeclone.api.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequest {
    @NotBlank(message = "Username is mandatory!")
    private String username;

    @NotBlank(message = "Password is mandatory!")
    @Size(min = 6, max = 18, message = "Password length must be 6 and 18 characters long!" )
    private String password;

    public AuthRequest() {}

    public AuthRequest(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
    ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
