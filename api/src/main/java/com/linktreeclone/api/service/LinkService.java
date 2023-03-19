package com.linktreeclone.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.linktreeclone.api.dao.LinkDao;
import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;
import com.linktreeclone.api.payload.output.PaginatedData;

@Service
public class LinkService {

    @Autowired
    private final LinkDao linkDao;

    private final PaginationService<Link> paginationService;

    public LinkService(@Qualifier("postgres-link") LinkDao linkDao) {
        this.linkDao = linkDao;
        this.paginationService = new PaginationService<>(linkDao);
    }

    public boolean addLink(Link link) {
        return linkDao.addLink(link);
    };

    public Optional<Link> selectLinkById(Long id) {
        return linkDao.selectLinkById(id);
    }

    public List<Link> selectAllLinks() {
        return linkDao.selectAllLinks();
    }
    
    public List<Link> selectAllLinksByCreatorId(Long creatorId) {
        return linkDao.selectAllLinksByCreatorId(creatorId);
    }
    
    public boolean deleteLinkById(Long id) {
        return linkDao.deleteLinkById(id);
    }

    public Optional<Link> updateLinkById(Long id, Link newLink) {
        return linkDao.updateLinkById(id, newLink);
    }

    public PaginatedData<Link> selectPaginatedLinksByCreatorId(
        Long creatorId, 
        PaginatedArgs paginatedArgs
    ) {
        return paginationService.selectPaginatedItemsByCreatorId(
            creatorId, 
            paginatedArgs
        );
    }

    public PaginatedData<Link> selectPaginatedSortedLinksByCreatorId(
        Long creatorId, 
        SortedPaginatedArgs paginatedArgs
    ) {
        return paginationService.selectPaginatedSortedItemsByCreatorId(
            creatorId, 
            paginatedArgs
        );
    }
}
