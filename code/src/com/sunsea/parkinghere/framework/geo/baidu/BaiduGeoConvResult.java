package com.sunsea.parkinghere.framework.geo.baidu;

import com.sunsea.parkinghere.framework.geo.Point;

// {"status":0,"result":[{"x":114.23074898012,"y":29.579085062759},{"x":114.23075384991,"y":29.579081330116}]}
public class BaiduGeoConvResult {
    
    int status;
    
    String message;
    
    Point[] result;
    
    public BaiduGeoConvResult() {
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Point[] getResult() {
        return result;
    }
    
    public void setResult(Point[] result) {
        this.result = result;
    }
    
}
