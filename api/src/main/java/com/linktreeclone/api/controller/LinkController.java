package com.linktreeclone.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.model.User;
import com.linktreeclone.api.repository.UserRepository;
import com.linktreeclone.api.security.service.UserDetailsImpl;
import com.linktreeclone.api.service.LinkService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class LinkController {
    
    @Autowired
    UserRepository userRepository;

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping 
    public ResponseEntity<List<Link>> getAllLinksByCreatorId(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long creatorId = userDetails.getId();
        List<Link> links = linkService.selectAllLinksByCreatorId(creatorId);
        return new ResponseEntity<List<Link>>(
            links, 
            HttpStatus.OK
        );
    }
    
    @PostMapping
    public boolean addLink(
        @Valid @RequestBody Link link,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isPresent()) {
            link.setCreator(user.get());
            linkService.addLink(link);
            return true;
        } else {
            return false;
        }
    }

    @DeleteMapping(path = "/link/{id}")
    public boolean deleteLink(
        @PathVariable("id") Long id
    ) {
        return linkService.deleteLinkById(id);
    }

    @PutMapping(path = "/link/{id}")
    public ResponseEntity<Link> updateLink(
        @PathVariable("id") Long id,
        @Valid @RequestBody Link updatedLink
    ) {
        Optional<Link> link = linkService.updateLinkById(id, updatedLink);
        if (link.isPresent()) {
            return new ResponseEntity<Link>(
                link.get(), 
                HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                null,
                HttpStatus.NOT_FOUND
            );
        }
    }
}
