package com.sunsea.parkinghere.module.audit.openapi;

public class AbstractQueryParameter {
    
    public static final int MIN_LIMIT_PER_PAGE = 100;
    
    public static final int MAX_LIMIT_PER_PAGE = 200;
    
    public static int getStart(AbstractQueryParameter queryParameter) {
        if (queryParameter.getStart() == null) {
            return 0;
        }
        int result = queryParameter.getStart().intValue();
        if (result < 0) {
            return 0;
        }
        return result;
    }
    
    public static int getLimit(AbstractQueryParameter queryParameter) {
        if (queryParameter.getLimit() == null) {
            return MIN_LIMIT_PER_PAGE;
        }
        int result = queryParameter.getLimit().intValue();
        if (result > MAX_LIMIT_PER_PAGE) {
            return MAX_LIMIT_PER_PAGE;
        }
        return result;
    }
    
    private Integer start;
    
    private Integer limit;
    
    public Integer getStart() {
        return start;
    }
    
    public void setStart(Integer start) {
        this.start = start;
    }
    
    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    
}
