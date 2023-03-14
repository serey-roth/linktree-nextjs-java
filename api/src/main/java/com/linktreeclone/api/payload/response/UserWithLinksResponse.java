package com.linktreeclone.api.payload.response;

import java.util.List;

import com.linktreeclone.api.model.Link;

public class UserWithLinksResponse extends UserResponse {

    private List<Link> links;

    public UserWithLinksResponse(
        Long id,
        String username,
        String email,
        List<String> roles,
        List<Link> links
    ) {
        super(id, username, email, roles);
        this.links = links;
    }
    
    public List<Link> getLinks() {
        return this.links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
    
}
