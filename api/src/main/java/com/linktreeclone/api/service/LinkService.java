package com.linktreeclone.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.linktreeclone.api.dao.LinkDao;
import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.payload.output.PaginatedData;

@Service
public class LinkService {

    @Autowired
    private final LinkDao linkDao;

    public LinkService(@Qualifier("postgres") LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    public boolean addLink(Link link) {
        return linkDao.addLink(link);
    };

    public Optional<Link> selectLinkById(Long id) {
        return linkDao.selectLinkById(id);
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
        int pageCount, 
        int pageNumber
    ) {
        Page<Link> links = linkDao.selectPaginatedLinksByCreatorId(
            creatorId, 
            pageCount, 
            pageNumber
        );

        int totalPage = links.getTotalPages();

        return new PaginatedData<Link>(
            totalPage,
            pageNumber,
            links.getContent()
        );
    }

    public PaginatedData<Link> selectPaginatedSortedLinksByCreatorId(
        Long creatorId, 
        int pageCount, 
        int pageNumber,
        String sortKey, 
        Direction order
    ) {
        Page<Link> links = linkDao.selectPaginatedSortedLinksByCreatorId(
            creatorId, 
            pageCount, 
            pageNumber, 
            sortKey, 
            order
        );

        int totalPage = links.getTotalPages();

        return new PaginatedData<Link>(
            totalPage,
            pageNumber,
            links.getContent()
        );
    }
}
