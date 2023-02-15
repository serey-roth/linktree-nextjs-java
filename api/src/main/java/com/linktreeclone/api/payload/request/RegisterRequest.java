package com.linktreeclone.api.payload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest extends AuthRequest {
    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Please enter a valid email address!")
    private String email;

    private Set<String> roles;
    
    public RegisterRequest() {}

    public RegisterRequest(String email, String username, String password) {
        super(username, password);
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return this.roles;
    }
    
    public void setRole(Set<String> roles) {
        this.roles = roles;
    }
}
