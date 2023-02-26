package com.linktreeclone.api.payload.response;

import java.util.List;

public class JwtResponse extends UserResponse {

    private String token;

    private String type = "Bearer";
  
    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        super(id, username, email, roles);
        this.token = accessToken;
    }
  
    public String getAccessToken() {
        return token;
    }
  
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }
  
    public String getTokenType() {
        return type;
    }
  
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}