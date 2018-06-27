package com.sunsea.parkinghere.framework.query;

import java.util.List;

public interface IMutablePage<T> extends IPage<T> {
    
    public abstract void setLimitInPage(int limit);
    
    public abstract void setCurrentPageNo(long currentPageNo);
    
    public abstract void setData(List<T> data);
    
    public abstract void setTotalDataCount(long totalDataCount);
    
}
