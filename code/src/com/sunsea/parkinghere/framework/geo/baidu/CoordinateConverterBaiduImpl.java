package com.sunsea.parkinghere.framework.geo.baidu;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.sunsea.parkinghere.Configuration;
import com.sunsea.parkinghere.framework.geo.CoordinateConverter;
import com.sunsea.parkinghere.framework.geo.CoordinateType;
import com.sunsea.parkinghere.framework.geo.IllegalCoordinateException;
import com.sunsea.parkinghere.framework.geo.Point;
import com.sunsea.parkinghere.framework.utils.IOUtils;
import com.sunsea.parkinghere.framework.utils.JsonUtils;

// http://api.map.baidu.com/geoconv/v1/?coords=114.21892734521,29.575429778924;114.21892734521,29.575429778924&ak=E4805d16520de693a3fe707cdc962045&output=json
public class CoordinateConverterBaiduImpl implements CoordinateConverter {
    
    private RestTemplate restTemplete = new RestTemplate();
    
    private String url;
    
    public CoordinateConverterBaiduImpl() {
        url = buildBaiduApiUrl();
    }
    
    private String buildBaiduApiUrl() {
        return new StringBuilder().append(Configuration.getInstance()
                                                       .getBaiduMapApi())
                                  .append("geoconv/v1/?ak=")
                                  .append(Configuration.getInstance()
                                                       .getBaiMapAk())
                                  .toString();
    }
    
    @Override
    public Point convert(Point p, CoordinateType from, CoordinateType to) {
        if (p == null) {
            return p;
        }
        if (from == to) {
            return p;
        }
        
        if (from == null || to == null) {
            throw new IllegalCoordinateException("The Convert CoordinateType must be supplied. ");
        }
        
        int fromType = getBaiduCoordinateType(from);
        int toType = getBaiduCoordinateType(to);
        
        if (toType != 5 && toType != 6) {
            throw new IllegalCoordinateException("Unsupported output type to convert.");
        }
        
        StringBuilder sb = new StringBuilder(url);
        sb.append("&coords=")
          .append(String.format("%s,%s", p.getX(), p.getY()))
          .append("&from=")
          .append(fromType)
          .append("&to=")
          .append(toType)
          .append("&output=json");
        
        System.out.println(sb.toString());
        // http://api.map.baidu.com/geoconv/v1/?coords=114.21892734521,29.575429778924;114.21892734521,29.575429778924&ak=E4805d16520de693a3fe707cdc962045&output=json
        BaiduGeoConvResult result = restTemplete.execute(sb.toString(),
                                                         HttpMethod.GET,
                                                         new RequestCallback() {
                                                             
                                                             @Override
                                                             public void doWithRequest(ClientHttpRequest request) throws IOException {
                                                             }
                                                         },
                                                         new ResponseExtractor<BaiduGeoConvResult>() {
                                                             
                                                             @Override
                                                             public BaiduGeoConvResult extractData(ClientHttpResponse response) throws IOException {
                                                                 HttpStatus statusCode = response.getStatusCode();
                                                                 if (statusCode != HttpStatus.OK) {
                                                                     throw new RuntimeException(String.format("访问百度坐标转化接口失败，返回值：%s,%s",
                                                                                                              statusCode,
                                                                                                              response.getStatusText()));
                                                                 }
                                                                 
                                                                 String bodyAsString = IOUtils.readAsString(response.getBody());
                                                                 
                                                                 System.out.println(bodyAsString);
                                                                 return (BaiduGeoConvResult) JsonUtils.parseJson(bodyAsString,
                                                                                                                 BaiduGeoConvResult.class);
                                                             }
                                                             
                                                         },
                                                         Collections.EMPTY_MAP);
        
        if (result.getStatus() != 0) {
            throw new IllegalCoordinateException("坐标转换失败，详细信息为：" + result.getMessage());
        }
        Point[] points = result.getResult();
        if (points == null || points.length == 0) {
            return null;
        }
        return points[0];
    }
    
    private int getBaiduCoordinateType(CoordinateType type) {
        if (type == null) {
            return -1;
        }
        
        switch (type) {
            case GPS_AC:
                return 1;
            case GPS_MC:
                return 2;
            case BAIDU_LL:
                return 5;
            case BAIDU_MC:
                return 6;
            case SOGOU:
                return 2;
            case GOOGLE_LL:
                return 3;
            case SOSO_LL:
                return 3;
            case GOOGLE_MC:
                return 4;
            case SOSO_MC:
                return 4;
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        Point in = new Point(30.653017, 104.130409);
        new CoordinateConverterBaiduImpl().convert(in,
                                                   CoordinateType.GOOGLE_LL,
                                                   CoordinateType.BAIDU_LL);
    }
}
