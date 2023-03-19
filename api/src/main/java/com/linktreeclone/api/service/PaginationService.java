package com.linktreeclone.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.linktreeclone.api.dao.Dao;
import com.linktreeclone.api.payload.output.PaginatedData;

@Service
public class PaginationService<T> {
    
    private final Dao<T> dao;

    public PaginationService(Dao<T> dao) {
        this.dao = dao;
    }

    public PaginatedData<T> selectPaginatedItemsByCreatorId(
        Long creatorId, 
        int pageCount, 
        int pageNumber
    ) {
        Page<T> items = dao.selectPaginatedItemsByCreatorId(
            creatorId, 
            pageCount, 
            pageNumber
        );

        int totalPage = items.getTotalPages();

        return new PaginatedData<T>(
            totalPage,
            pageNumber,
            items.getContent()
        );
    }

    public PaginatedData<T> selectPaginatedSortedItemsByCreatorId(
        Long creatorId, 
        int pageCount, 
        int pageNumber,
        String sortKey, 
        Direction order
    ) {
        Page<T> items = dao.selectPaginatedSortedItemsByCreatorId(
            creatorId, 
            pageCount, 
            pageNumber, 
            sortKey, 
            order
        );

        int totalPage = items.getTotalPages();

        return new PaginatedData<T>(
            totalPage,
            pageNumber,
            items.getContent()
        );
    }
}
