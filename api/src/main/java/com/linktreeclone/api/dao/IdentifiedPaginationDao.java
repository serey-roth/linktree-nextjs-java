package com.linktreeclone.api.dao;

import org.springframework.data.domain.Page;

import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;

public interface IdentifiedPaginationDao<T> extends PaginationDao<T> {
    
    Page<T> selectPaginatedItemsByIdentifier(Long identifier, PaginatedArgs args);

    Page<T> selectPaginatedSortedItemsByIdentifier(Long identifier, SortedPaginatedArgs args);
    
}
