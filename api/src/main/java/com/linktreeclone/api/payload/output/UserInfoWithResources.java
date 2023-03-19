package com.linktreeclone.api.payload.output;

import java.util.List;

public class UserInfoWithResources<T> extends UserInfo {

    private T resources;

    public UserInfoWithResources(
        Long id,
        String username,
        String email,
        List<String> roles,
        T resources
    ) {
        super(id, username, email, roles);
        this.resources = resources;
    }
    
    public T getResources() {
        return this.resources;
    }

    public void setResources(T resources) {
        this.resources = resources;
    }
    
}
