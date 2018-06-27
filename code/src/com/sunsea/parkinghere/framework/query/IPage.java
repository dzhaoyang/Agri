package com.sunsea.parkinghere.framework.query;

import java.util.List;

public interface IPage<T> {
    
    public abstract long getTotalDataCount();
    
    public abstract long getTotalPageCount();
    
    public abstract int getLimitInPage();
    
    public abstract int getStartOfPage();
    
    public abstract List<T> getData();
    
    public abstract long getCurrentPageNo();
    
    public abstract boolean hasNextPage();
    
    public abstract boolean hasPreviousPage();
    
}
