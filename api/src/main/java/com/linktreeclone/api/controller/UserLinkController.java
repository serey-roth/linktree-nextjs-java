package com.linktreeclone.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linktreeclone.api.exception.NotFoundException;
import com.linktreeclone.api.model.Link;
import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.response.ApiResponse;
import com.linktreeclone.api.payload.response.UserWithLinksResponse;
import com.linktreeclone.api.repository.UserRepository;
import com.linktreeclone.api.service.LinkService;

import jakarta.validation.constraints.NotBlank;

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
    public ResponseEntity<ApiResponse<UserWithLinksResponse>> getAllLinksByCreatorId(
        @NotBlank(message = "Username is mandatory!")
        @PathVariable("username") String username
    ) throws RuntimeException {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            List<Link> links = linkService.selectAllLinksByCreatorId(user.getId());
            ApiResponse<UserWithLinksResponse> response = new ApiResponse<>(
                new UserWithLinksResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRoles()
                        .stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList()),
                    links
                ),
                null
            );
            return new ResponseEntity<ApiResponse<UserWithLinksResponse>>(
                response,
                HttpStatus.OK
            );
        } else {
            throw new NotFoundException("User not found!", "No user with this username!");
        }
    }

}
