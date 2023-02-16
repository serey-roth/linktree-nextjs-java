package com.linktreeclone.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.repository.LinkRepository;

@Repository("postgres")
public class LinkDaoPostgresImpl implements LinkDao {

    @Autowired
    LinkRepository linkRepository;

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
    
}
