package com.wyk.dsmanager.utils;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 通用分页对象
 */
public class PageResult {
    public PageResult(int pageSize, int currentPage, long total, List list) {
        this.setPageSize(pageSize);
        this.setCurrentPage(currentPage);
        this.setRows(list);
        this.setTotal(total);
    }

    private int currentPage;
    private int pageSize;
    private long total;
    private int pageCount;
    private List<?> rows;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        pageCount = (int)(total % (long)pageSize == 0 ? total / (long)pageSize : (total / (long)pageSize + 1));
        this.setPageCount(pageCount);
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public boolean isFirstPage() {
        return currentPage == 1;
    }

    public boolean isLastPage() {
        return (this.total == 0) || (this.currentPage >= getPageCount());
    }

    public boolean isHasNext() {
        return this.currentPage < getPageCount();
    }

    public boolean isHasPrev() {
        return this.currentPage > 1;
    }

    public Integer getNextPage() {
        if (this.currentPage >= getPageCount()) {
            return getPageCount();
        }
        return this.currentPage + 1;
    }

    public Integer getPrevPage() {
        if (this.currentPage <= 1) {
            return 1;
        }
        return this.currentPage - 1;
    }

    public static PageResult setPageResult(int pageSize, int currentPage, long total, List list) {
        return new PageResult(pageSize, currentPage, total, list);
    }

    public static PageResult setPageResult(Pageable pageable, long total, List list) {
        return new PageResult(pageable.getPageSize(), pageable.getPageNumber() + 1, total, list);
    }
}
