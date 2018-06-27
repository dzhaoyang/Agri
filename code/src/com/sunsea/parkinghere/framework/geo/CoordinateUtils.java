package com.sunsea.parkinghere.framework.geo;

import com.sunsea.parkinghere.framework.geo.baidu.CoordinateConverterBaiduImpl;

public abstract class CoordinateUtils {
    
    static CoordinateConverter converter = new CoordinateConverterBaiduImpl();
    
    public static Point convert(Point p, CoordinateType from, CoordinateType to) {
        return converter.convert(p, from, to);
    }
    
}
