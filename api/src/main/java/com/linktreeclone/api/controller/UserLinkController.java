package com.linktreeclone.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linktreeclone.api.exception.NotFoundException;
import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;
import com.linktreeclone.api.payload.output.ApiResult;
import com.linktreeclone.api.payload.output.PaginatedData;
import com.linktreeclone.api.payload.output.UserInfoWithResources;
import com.linktreeclone.api.repository.UserRepository;
import com.linktreeclone.api.service.LinkService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
public class UserLinkController {
    
    @Autowired
    UserRepository userRepository;

    private final LinkService linkService;

    @Autowired
    public UserLinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * @param username
     * @return
     * @throws RuntimeException
     */
    @GetMapping(path = "/{username}")
    public ResponseEntity<ApiResult<UserInfoWithResources<List<Link>>>> getUserWithLinks(
        @Valid @NotBlank(message = "Username is mandatory!")
        @PathVariable("username") String username
    ) throws RuntimeException {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            List<Link> links = linkService.selectAllLinksByCreatorId(user.getId());
            ApiResult<UserInfoWithResources<List<Link>>> response = new ApiResult<>(
                generatePaginatedResponse(
                    user, 
                    links
                ),
                null
            );
            return new ResponseEntity<ApiResult<UserInfoWithResources<List<Link>>>>(
                response,
                HttpStatus.OK
            );
        } else {
            throw new NotFoundException("User not found!", "No user with this username!");
        }
    }

    @GetMapping(path = "/{username}/paginated")
    public ResponseEntity<ApiResult<UserInfoWithResources<PaginatedData<Link>>>> getUserWithPaginatedLinks(
        @Valid @NotBlank(message = "Username is mandatory!") 
        @PathVariable("username") String username,
        @Valid @NotNull @RequestParam(defaultValue = "5") String pageCount,
        @Valid @NotNull @RequestParam(defaultValue = "0") String pageNumber
    ) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            PaginatedArgs paginatedRequest = new PaginatedArgs(pageCount, pageNumber);
            PaginatedData<Link> links = linkService.selectPaginatedLinksByCreatorId(
                user.getId(), 
                paginatedRequest.getPageCount(), 
                paginatedRequest.getPageNumber()
            );
        
            ApiResult<UserInfoWithResources<PaginatedData<Link>>> response = new ApiResult<>(
                generatePaginatedResponse(user, links),
                null
            );
            return new ResponseEntity<ApiResult<UserInfoWithResources<PaginatedData<Link>>>>(
                response,
                HttpStatus.OK
            );
        } else {
            throw new NotFoundException("User not found!", "No user with this username!");
        }
    }
    
    @GetMapping(path = "/{username}/paginated-sorted")
    public ResponseEntity<ApiResult<UserInfoWithResources<PaginatedData<Link>>>> getUserWithSortedPaginatedLinks(
        @Valid @NotBlank(message = "Username is mandatory!") 
        @PathVariable("username") String username,
        @Valid @NotNull @RequestParam(defaultValue = "5") String pageCount,
        @Valid @NotNull @RequestParam(defaultValue = "1") String pageNumber,
        @Valid @NotNull @RequestParam String sortKey,
        @Valid @NotNull @RequestParam Direction order
    ) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            SortedPaginatedArgs paginatedRequest = new SortedPaginatedArgs(
                pageCount,
                pageNumber,
                order,
                sortKey
            );

            PaginatedData<Link> links = linkService.selectPaginatedSortedLinksByCreatorId(
                user.getId(), 
                paginatedRequest.getPageCount(), 
                paginatedRequest.getPageNumber(),
                paginatedRequest.getSortKey(),
                paginatedRequest.getOrder()
            );

            ApiResult<UserInfoWithResources<PaginatedData<Link>>> response = new ApiResult<>(
                generatePaginatedResponse(user, links),
                null
            );
            return new ResponseEntity<ApiResult<UserInfoWithResources<PaginatedData<Link>>>>(
                response,
                HttpStatus.OK
            );
        } else {
            throw new NotFoundException("User not found!", "No user with this username!");
        }
    }

    private <T> UserInfoWithResources<T> generatePaginatedResponse(
        User user,
        T resources
    ) {
        return new UserInfoWithResources<T>(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList()),
            resources
        );
    }
}
