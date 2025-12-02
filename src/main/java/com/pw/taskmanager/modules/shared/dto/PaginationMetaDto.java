package com.pw.taskmanager.modules.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PaginationMetaDto")
public class PaginationMetaDto {

    @Schema(example = "true")
    private boolean isFirstPage;

    @Schema(example = "false")
    private boolean isLastPage;

    @Schema(example = "1")
    private int currentPage;

    @Schema(example = "null")
    private Integer previousPage;

    @Schema(example = "2")
    private Integer nextPage;

    @Schema(example = "3")
    private int pageCount;

    @Schema(example = "55")
    private long totalCount;

    public PaginationMetaDto() {}

    // getters / setters
    public boolean isFirstPage() { return isFirstPage; }
    public void setFirstPage(boolean firstPage) { isFirstPage = firstPage; }
    public boolean isLastPage() { return isLastPage; }
    public void setLastPage(boolean lastPage) { isLastPage = lastPage; }
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public Integer getPreviousPage() { return previousPage; }
    public void setPreviousPage(Integer previousPage) { this.previousPage = previousPage; }
    public Integer getNextPage() { return nextPage; }
    public void setNextPage(Integer nextPage) { this.nextPage = nextPage; }
    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
    public long getTotalCount() { return totalCount; }
    public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
}
