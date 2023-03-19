package com.linktreeclone.api.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

public interface Dao<T> {
    
    List<T> selectAllItemsByCreatorId(Long creatorId);

    Page<T> selectPaginatedItemsByCreatorId(Long creatorId, int pageCount, int pageNumber);

    Page<T> selectPaginatedSortedItemsByCreatorId(Long creatorId, int pageCount, int pageNumber, String sortBy, Direction order);
}
