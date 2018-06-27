package com.sunsea.parkinghere.framework.geo;

public interface CoordinateConverter {
    
    public Point convert(Point p, CoordinateType from, CoordinateType to);
    
}
