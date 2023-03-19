package com.linktreeclone.api.dao;

import java.util.List;
import java.util.Optional;

import com.linktreeclone.api.model.Link;

public interface LinkDao extends IdentifiedPaginationDao<Link> {
    
    List<Link> selectAllLinks();
    
    boolean addLink(Link link);

    Optional<Link> selectLinkById(Long id);
    
    boolean deleteLinkById(Long id);

    Optional<Link> updateLinkById(Long id, Link newLink);

    List<Link> selectAllLinksByCreatorId(Long creatorId);

}
