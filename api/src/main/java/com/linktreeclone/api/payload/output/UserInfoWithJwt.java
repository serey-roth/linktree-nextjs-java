package com.linktreeclone.api.payload.output;

import java.util.List;

public class UserInfoWithJwt extends UserInfo {

    private String token;

    private String type = "Bearer";
  
    public UserInfoWithJwt(String accessToken, Long id, String username, String email, List<String> roles) {
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