package com.linktreeclone.api.payload.response;

import java.util.List;

public class PaginatedResponse<T> {
    private int totalPages;

    private int currentPage;

    private List<T> data;

    public PaginatedResponse() {}

    public PaginatedResponse(int totalPages, int currentPage, List<T> data) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.data = data;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
