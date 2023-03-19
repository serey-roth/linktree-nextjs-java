package com.linktreeclone.api.payload.input;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsernameEmailPassword extends UsernamePassword {
    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Email is invalid!")
    private String email;

    private Set<String> roles;
    
    public UsernameEmailPassword() {}

    public UsernameEmailPassword(
        @JsonProperty("email") String email, 
        @JsonProperty("username") String username, 
        @JsonProperty("password") String password,
        @JsonProperty("roles") List<String> roles
    ) {
        super(username, password);
        this.email = email;
        this.roles = Set.copyOf(roles);
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
