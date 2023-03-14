package com.linktreeclone.api.payload.response;

import java.util.List;

public class UserWithResourcesResponse<T> extends UserResponse {

    private T resources;

    public UserWithResourcesResponse(
        Long id,
        String username,
        String email,
        List<String> roles,
        T resources
    ) {
        super(id, username, email, roles);
        this.resources = resources;
    }
    
    public T getLinks() {
        return this.resources;
    }

    public void setLinks(T resources) {
        this.resources = resources;
    }
    
}
