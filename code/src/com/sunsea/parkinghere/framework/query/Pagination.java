package com.sunsea.parkinghere.framework.query;

public class Pagination {
    
    private int start = 0;
    
    private int limit = 10;
    
    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }
    
    /**
     * @param start
     *            the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }
    
    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }
    
    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
}
