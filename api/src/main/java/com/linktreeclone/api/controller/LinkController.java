package com.linktreeclone.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linktreeclone.api.exception.NotFoundException;
import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.request.PaginatedRequest;
import com.linktreeclone.api.payload.request.SortedPaginatedRequest;
import com.linktreeclone.api.repository.UserRepository;
import com.linktreeclone.api.security.service.UserDetailsImpl;
import com.linktreeclone.api.service.LinkService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/secure")
public class LinkController {
    
    @Autowired
    UserRepository userRepository;

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(path = "/links/all")
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
    
    @GetMapping(path = "/links/paginated")
    public ResponseEntity<List<Link>> getPaginatedLinksByCreatorId(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @NotNull @RequestBody PaginatedRequest paginatedRequest
    ) {
        Long creatorId = userDetails.getId();
        List<Link> links = linkService.selectPaginatedLinksByCreatorId(
            creatorId, 
            paginatedRequest.getPageCount(), 
            paginatedRequest.getPageNumber()
        );
        return new ResponseEntity<List<Link>>(
            links, 
            HttpStatus.OK
        );
    }

    @GetMapping("/links/paginated_sorted")
    public ResponseEntity<List<Link>> getPaginatedSortedLinksByCreatorId(
        @Valid @NotNull @RequestBody SortedPaginatedRequest paginatedRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long creatorId = userDetails.getId();
        List<Link> links = linkService.selectPaginatedSortedLinksByCreatorId(
            creatorId, 
            paginatedRequest.getPageCount(), 
            paginatedRequest.getPageNumber(),
            paginatedRequest.getSortKey(),
            paginatedRequest.getOrder()
        );
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

    @GetMapping(path = "/link/{id}")
    public ResponseEntity<Link> getLinkById(
        @PathVariable("id") Long id
    ) {
        Optional<Link> link = linkService.selectLinkById(id);
        if (link.isPresent()) {
            return new ResponseEntity<Link>(
                link.get(), 
                HttpStatus.OK
            );
        } else {
            throw new NotFoundException(
                "No link found with id: " + id,
                "Requested link cannot be found! Please enter a valid id!"
            );
        }
    }

    @DeleteMapping(path = "/link/{id}")
    public boolean deleteLink(
        @PathVariable("id") Long id
    ) {
        boolean isDeleted = linkService.deleteLinkById(id);
        if (!isDeleted) {
            throw new NotFoundException(
                "No link found with id: " + id,
                "Link cannot be deleted due to invalid id. Please enter a valid id!"
            );
        }
        return isDeleted;
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
            throw new NotFoundException(
                "No link found with id: " + id,
                "Link cannot be updated due to invalid id. Please enter a valid id!"
            );
        }
    }
}
