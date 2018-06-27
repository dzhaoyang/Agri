package com.sunsea.parkinghere.framework.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ImmutablePage<T> implements Serializable, IPage<T> {
    
    public static final long serialVersionUID = 1L;
    
    public static final int DEFAULT_DATA_SIZE_IN_ONE_PAGE = 20;
    
    protected int dataCountInOnePage = DEFAULT_DATA_SIZE_IN_ONE_PAGE;
    
    protected long currentPageNo;
    
    protected List<T> data;
    
    protected long totalDataCount;
    
    /**
     * 
     */
    public ImmutablePage() {
        this(0, 0, DEFAULT_DATA_SIZE_IN_ONE_PAGE, Collections.EMPTY_LIST);
    }
    
    public ImmutablePage(long currentPageNo,
                         long totalCount,
                         int limitPerPage,
                         List<T> data) {
        if (0 > limitPerPage)
            throw new IllegalStateException("");
        this.currentPageNo = currentPageNo;
        this.totalDataCount = totalCount;
        this.dataCountInOnePage = limitPerPage;
        this.data = data;
    }
    
    public long getTotalDataCount() {
        return this.totalDataCount;
    }
    
    public long getTotalPageCount() {
        if (totalDataCount % dataCountInOnePage == 0)
            return totalDataCount / dataCountInOnePage;
        else
            return totalDataCount / dataCountInOnePage + 1;
    }
    
    public int getLimitInPage() {
        return dataCountInOnePage;
    }
    
    public int getStartOfPage() {
        return getStartOfPage((int) getCurrentPageNo(), getLimitInPage());
    }
    
    public List<T> getData() {
        return data;
    }
    
    public long getCurrentPageNo() {
        return currentPageNo;
    }
    
    public boolean hasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
    }
    
    public boolean hasPreviousPage() {
        return this.getCurrentPageNo() > 0;
    }
    
    public static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_DATA_SIZE_IN_ONE_PAGE);
    }
    
    public static int getStartOfPage(int pageNo, int pageSize) {
        if (0 > pageNo)
            throw new IllegalArgumentException("!");
        return pageNo * pageSize;
    }
    
}
