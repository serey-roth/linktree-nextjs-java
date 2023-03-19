package com.linktreeclone.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;
import com.linktreeclone.api.repository.UserRepository;

@Repository("postgres-user")
public class UserDaoImpl implements PaginationDao<User> {

    @Autowired
    UserRepository userRepository;

    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> selectPaginatedItems(PaginatedArgs args) {
        Pageable page = PageRequest.of(
            args.getPageNumber(), 
            args.getPageCount()
        );
        return userRepository.findAll(page);
    }

    @Override
    public Page<User> selectPaginatedSortedItems(SortedPaginatedArgs args) {
        Pageable page = PageRequest.of(
            args.getPageNumber(), 
            args.getPageCount()
        ).withSort(args.getOrder(), args.getSortKey());
        return userRepository.findAll(page);
    }

}
