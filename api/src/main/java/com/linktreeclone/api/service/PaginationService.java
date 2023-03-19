package com.linktreeclone.api.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.linktreeclone.api.dao.IdentifiedPaginationDao;
import com.linktreeclone.api.dao.PaginationDao;
import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;
import com.linktreeclone.api.payload.output.PaginatedData;

@Service
public class PaginationService<TData> {
    
    private final PaginationDao<TData> dao;

    public PaginationService(PaginationDao<TData> dao) {
        this.dao = dao;
    }

    public PaginatedData<TData> selectPaginatedItems(PaginatedArgs paginatedArgs) {
        Page<TData> items =  dao.selectPaginatedItems(paginatedArgs);

        int totalPage = items.getTotalPages();

        return new PaginatedData<TData>(
            totalPage,
            paginatedArgs.getPageNumber(),
            items.getContent()
        );
    }

    public PaginatedData<TData> selectPaginatedSortedItems(SortedPaginatedArgs paginatedArgs) {
        Page<TData> items = dao.selectPaginatedSortedItems(paginatedArgs);

        int totalPage = items.getTotalPages();

        return new PaginatedData<TData>(
            totalPage,
            paginatedArgs.getPageNumber(),
            items.getContent()
        );
    }

    public PaginatedData<TData> selectPaginatedItemsByCreatorId(
        Long creatorId, 
        PaginatedArgs paginatedArgs
    ) {
        Page<TData> items =  ((IdentifiedPaginationDao<TData>) dao).selectPaginatedItemsByIdentifier(
            creatorId, 
            paginatedArgs
        );

        int totalPage = items.getTotalPages();

        return new PaginatedData<TData>(
            totalPage,
            paginatedArgs.getPageNumber(),
            items.getContent()
        );
    }

    public PaginatedData<TData> selectPaginatedSortedItemsByCreatorId(
        Long creatorId, 
        SortedPaginatedArgs paginatedArgs
    ) {
        Page<TData> items = ((IdentifiedPaginationDao<TData>) dao).selectPaginatedSortedItemsByIdentifier(
            creatorId, 
            paginatedArgs
        );

        int totalPage = items.getTotalPages();

        return new PaginatedData<TData>(
            totalPage,
            paginatedArgs.getPageNumber(),
            items.getContent()
        );
    }
}
