package com.linktreeclone.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;
import com.linktreeclone.api.repository.LinkRepository;

@Primary
@Repository("postgres-link")
public class LinkDaoImpl implements LinkDao {

    @Autowired
    LinkRepository linkRepository;

    public List<Link> selectAllLinks() {
        return linkRepository.findAll();
    }

    @Override
    public boolean addLink(Link link) {
        linkRepository.save(link);
        return true;
    }

    @Override
    public Optional<Link> selectLinkById(Long id) {
        return linkRepository.findById(id);
    }

    @Override
    public List<Link> selectAllLinksByCreatorId(Long creatorId) {
        return linkRepository.findAllByCreatorId(creatorId);
    }

    @Override
    public boolean deleteLinkById(Long id) {
        boolean existedLink = linkRepository.existsById(id);
        if (existedLink) {
            linkRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Link> updateLinkById(Long id, Link newLink) {
        Optional<Link> link = linkRepository.findById(id);
        if (link.isPresent()) {
            Link actuaLink = link.get();
            actuaLink.setUrl(newLink.getUrl());
            actuaLink.setDescription(newLink.getDescription());
            actuaLink.setTitle(newLink.getTitle());
            linkRepository.save(actuaLink);
            return Optional.of(actuaLink);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<Link> selectPaginatedItemsByIdentifier(
        Long creatorId, 
        PaginatedArgs args
    ) {
        Pageable page = PageRequest.of(args.getPageNumber(), args.getPageCount());
        return linkRepository.findAllByCreatorId(creatorId, page);
    }

    @Override
    public Page<Link> selectPaginatedSortedItemsByIdentifier(
        Long creatorId, 
        SortedPaginatedArgs args
    ) {
        Pageable page = PageRequest.of(
            args.getPageNumber(), 
            args.getPageCount()
        ).withSort(args.getOrder(), args.getSortKey());
        return linkRepository.findAllByCreatorId(creatorId, page);
    }

    @Override
    public Page<Link> selectPaginatedItems(PaginatedArgs args) {
        Pageable page = PageRequest.of(args.getPageNumber(), args.getPageCount());
        return linkRepository.findAll(page);
    }

    @Override
    public Page<Link> selectPaginatedSortedItems(
        SortedPaginatedArgs args
    ) {
        Pageable page = PageRequest.of(
            args.getPageNumber(), 
            args.getPageCount()
        ).withSort(args.getOrder(), args.getSortKey());
        return linkRepository.findAll(page);
    }
    
}
