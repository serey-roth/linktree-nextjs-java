package com.linktreeclone.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.response.ApiResponse;
import com.linktreeclone.api.payload.response.PaginatedResponse;
import com.linktreeclone.api.payload.response.UserResponse;
import com.linktreeclone.api.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UsersController {
    
    @Autowired
    UserRepository userRepository;

    /**
     * @param username
     * @return
     * @throws RuntimeException
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<ApiResponse<List<UserResponse>>>(
            new ApiResponse<List<UserResponse>>(
                users.stream().map(user -> new UserResponse(
                    user.getId(), 
                    user.getUsername(), 
                    user.getEmail(), 
                    user.getRoles()
                        .stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList()))
            ).collect(Collectors.toList()),
                null
            ),
            HttpStatus.OK
        );
    }

    @GetMapping(path = "/paginated")
    public ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>> getPaginatedUsers(
        @Valid @NotNull @RequestParam(defaultValue = "5") String pageCount,
        @Valid @NotNull @RequestParam(defaultValue = "0") String pageNumber
    ) {
        Pageable page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageCount));
        Page<User> users = userRepository.findAll(page);

        return new ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>>(
            new ApiResponse<PaginatedResponse<UserResponse>>(
                new PaginatedResponse<>(
                    users.getTotalPages(), 
                    page.getPageNumber(), 
                    users.getContent().stream().map(user -> new UserResponse(
                        user.getId(), 
                        user.getUsername(), 
                        user.getEmail(), 
                        user.getRoles()
                            .stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toList()))
                    ).collect(Collectors.toList())
                ), null
            ),
            HttpStatus.OK
        );
    }
    
    @GetMapping(path = "/paginated-sorted")
    public ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>> getSortedPaginatedUsers(
        @Valid @NotNull @RequestParam(defaultValue = "5") String pageCount,
        @Valid @NotNull @RequestParam(defaultValue = "1") String pageNumber,
        @Valid @NotNull @RequestParam String sortKey,
        @Valid @NotNull @RequestParam Direction order
    ) {
        Pageable page = PageRequest.of(
            Integer.parseInt(pageNumber),
            Integer.parseInt(pageCount)
        ).withSort(order, sortKey);
        Page<User> users = userRepository.findAll(page);

        return new ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>>(
            new ApiResponse<PaginatedResponse<UserResponse>>(
                new PaginatedResponse<>(
                    users.getTotalPages(), 
                    page.getPageNumber(), 
                    users.getContent().stream().map(user -> new UserResponse(
                        user.getId(), 
                        user.getUsername(), 
                        user.getEmail(), 
                        user.getRoles()
                            .stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toList()))
                    ).collect(Collectors.toList())
                ), null
            ),
            HttpStatus.OK
        );
    }
}
