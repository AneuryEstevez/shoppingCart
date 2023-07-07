package org.example.Products;

public class Pagination {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int totalCount;

    public Pagination(int currentPage, int totalPages, int pageSize, int totalCount) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

