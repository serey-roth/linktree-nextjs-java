package com.linktreeclone.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.linktreeclone.api.dao.UserDaoImpl;
import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;
import com.linktreeclone.api.payload.output.PaginatedData;
import com.linktreeclone.api.payload.output.UserInfo;

@Service
public class UserService {
    
    @Autowired
    private final UserDaoImpl userDao;

    private final PaginationService<User> paginationService;

    public UserService(@Qualifier("postgres-user") UserDaoImpl userDao) {
        this.userDao = userDao;
        this.paginationService = new PaginationService<>(userDao);
    }

    public List<UserInfo> selectAllUsers() {
        return userDao
            .selectAllUsers()
            .stream()
            .map(user -> generateUserInfo(user))
            .toList();
    }

    public PaginatedData<UserInfo> selectPaginatedUsers(PaginatedArgs args) {
        PaginatedData<User> usersPagination = paginationService.selectPaginatedItems(args);
        
        return new PaginatedData<UserInfo>(
            usersPagination.getTotalPages(), 
            args.getPageNumber(), 
            usersPagination
                .getData()
                .stream()
                .map(user -> generateUserInfo(user))
                .toList()
        );
    }

    public PaginatedData<UserInfo> selectPaginatedSortedUsers(SortedPaginatedArgs args) {
        PaginatedData<User> usersPagination = paginationService.selectPaginatedSortedItems(args);

        return new PaginatedData<UserInfo>(
            usersPagination.getTotalPages(), 
            args.getPageNumber(), 
            usersPagination
                .getData()
                .stream()
                .map(user -> generateUserInfo(user))
                .toList()
        );
    }

    private UserInfo generateUserInfo(User user) {
        return new UserInfo(
            user.getId(), 
            user.getUsername(), 
            user.getEmail(), 
            user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList())
        );
    }
    
}
