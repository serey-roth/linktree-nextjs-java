package com.linktreeclone.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.linktreeclone.api.model.Link;

public interface LinkDao {
    
    boolean addLink(Link link);

    Optional<Link> selectLinkById(Long id);
    
    List<Link> selectAllLinksByCreatorId(Long creatorId);
    
    boolean deleteLinkById(Long id);

    Optional<Link> updateLinkById(Long id, Link newLink);

    Page<Link> selectPaginatedLinksByCreatorId(Long creatorId, int pageCount, int pageNumber);

    Page<Link> selectPaginatedSortedLinksByCreatorId(Long creatorId, int pageCount, int pageNumber, String sortBy, Direction order);
}
