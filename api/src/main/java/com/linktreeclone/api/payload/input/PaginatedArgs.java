package com.linktreeclone.api.payload.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;

public class PaginatedArgs {
    @Min(value = 1L, message = "Page size must be at least 1!")
    private int pageCount;

    @Min(value = 1L, message = "Page number must be at least 1!")
    private int pageNumber;

    public PaginatedArgs() {};

    public PaginatedArgs(
        @JsonProperty("pageCount") String pageCount, 
        @JsonProperty("pageNumber") String pageNumber
    ) {
        this.pageCount = Integer.parseInt(pageCount);
        this.pageNumber =  Integer.parseInt(pageNumber);
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
