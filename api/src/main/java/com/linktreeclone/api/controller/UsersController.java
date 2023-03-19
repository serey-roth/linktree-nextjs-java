package com.linktreeclone.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;
import com.linktreeclone.api.payload.output.ApiResult;
import com.linktreeclone.api.payload.output.PaginatedData;
import com.linktreeclone.api.payload.output.UserInfo;
import com.linktreeclone.api.repository.UserRepository;
import com.linktreeclone.api.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UsersController {
    
    @Autowired
    UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param username
     * @return
     * @throws RuntimeException
     */
    @GetMapping
    public ResponseEntity<ApiResult<List<UserInfo>>> getAllUsers() {
        List<UserInfo> users = userService.selectAllUsers();
        
        return new ResponseEntity<ApiResult<List<UserInfo>>>(
            new ApiResult<List<UserInfo>>(users, null),
            HttpStatus.OK
        );
    }

    @GetMapping(path = "/paginated")
    public ResponseEntity<ApiResult<PaginatedData<UserInfo>>> getPaginatedUsers(
        @Valid @NotNull @RequestParam(defaultValue = "5") String pageCount,
        @Valid @NotNull @RequestParam(defaultValue = "0") String pageNumber
    ) {
        PaginatedArgs paginatedArgs = new PaginatedArgs(pageCount, pageNumber);
        
        PaginatedData<UserInfo> users = userService.selectPaginatedUsers(paginatedArgs);

        return new ResponseEntity<ApiResult<PaginatedData<UserInfo>>>(
            new ApiResult<PaginatedData<UserInfo>>(users, null),
            HttpStatus.OK
        );
    }
    
    @GetMapping(path = "/paginated-sorted")
    public ResponseEntity<ApiResult<PaginatedData<UserInfo>>> getSortedPaginatedUsers(
        @Valid @NotNull @RequestParam(defaultValue = "5") String pageCount,
        @Valid @NotNull @RequestParam(defaultValue = "1") String pageNumber,
        @Valid @NotNull @RequestParam String sortKey,
        @Valid @NotNull @RequestParam Direction order
    ) {
        SortedPaginatedArgs paginatedArgs = new SortedPaginatedArgs(
            pageCount,
            pageNumber,
            order,
            sortKey
        );
        
        PaginatedData<UserInfo> users = userService.selectPaginatedSortedUsers(paginatedArgs);
        
        return new ResponseEntity<ApiResult<PaginatedData<UserInfo>>>(
            new ApiResult<PaginatedData<UserInfo>>(users, null),
            HttpStatus.OK
        );
    }
}
