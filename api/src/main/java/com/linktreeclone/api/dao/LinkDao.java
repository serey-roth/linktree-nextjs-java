package com.linktreeclone.api.dao;

import java.util.List;
import java.util.Optional;

import com.linktreeclone.api.model.Link;

public interface LinkDao {
    
    boolean addLink(Link link);

    Optional<Link> selectLinkById(Long id);
    
    List<Link> selectAllLinksByCreatorId(Long creatorId);
    
    boolean deleteLinkById(Long id);

    Optional<Link> updateLinkById(Long id, Link newLink);
}
