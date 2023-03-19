package com.linktreeclone.api.dao;

import java.util.Optional;

import com.linktreeclone.api.model.Link;

public interface LinkDao extends Dao<Link> {
    
    boolean addLink(Link link);

    Optional<Link> selectLinkById(Long id);
    
    boolean deleteLinkById(Long id);

    Optional<Link> updateLinkById(Long id, Link newLink);
}
