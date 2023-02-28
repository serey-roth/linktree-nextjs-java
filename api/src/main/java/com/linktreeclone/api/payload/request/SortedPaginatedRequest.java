package com.linktreeclone.api.payload.request;

import java.util.Objects;

import org.springframework.data.domain.Sort.Direction;

import jakarta.validation.constraints.NotBlank;

public class SortedPaginatedRequest extends PaginatedRequest {
    private Direction order;

    @NotBlank(message = "Sorting key is mandatory")
    private String sortKey;

    public SortedPaginatedRequest() {};

    public SortedPaginatedRequest(
        String pageCount, 
        String pageNumber, 
        Direction order, 
        String sortKey
    ) {
        super(pageCount, pageNumber);
        this.order = order;
        this.sortKey = sortKey;
    }

    public Direction getOrder() {
        return this.order;
    }

    public void setOrder(Direction order) {
        this.order = order;
    }

    public String getSortKey() {
        return this.sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public SortedPaginatedRequest order(Direction order) {
        setOrder(order);
        return this;
    }

    public SortedPaginatedRequest sortKey(String sortKey) {
        setSortKey(sortKey);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SortedPaginatedRequest)) {
            return false;
        }
        SortedPaginatedRequest sortedPaginatedRequest = (SortedPaginatedRequest) o;
        return Objects.equals(order, sortedPaginatedRequest.order) && Objects.equals(sortKey, sortedPaginatedRequest.sortKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, sortKey);
    }

    @Override
    public String toString() {
        return "{" +
            " order='" + getOrder() + "'" +
            ", sortKey='" + getSortKey() + "'" +
            "}";
    }
    
}
