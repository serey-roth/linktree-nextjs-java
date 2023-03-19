package com.linktreeclone.api.dao;

import org.springframework.data.domain.Page;

import com.linktreeclone.api.payload.input.PaginatedArgs;
import com.linktreeclone.api.payload.input.SortedPaginatedArgs;

public interface PaginationDao<T> {

    Page<T> selectPaginatedItems(PaginatedArgs args);

    Page<T> selectPaginatedSortedItems(SortedPaginatedArgs args);

}
