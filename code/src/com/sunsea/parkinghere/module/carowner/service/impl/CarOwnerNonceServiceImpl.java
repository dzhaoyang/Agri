package com.sunsea.parkinghere.module.carowner.service.impl;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.framework.geo.CoordinateType;
import com.sunsea.parkinghere.framework.geo.CoordinateUtils;
import com.sunsea.parkinghere.framework.geo.Point;
import com.sunsea.parkinghere.module.carowner.controller.CarOwnerDebugerContoller;
import com.sunsea.parkinghere.module.carowner.model.CarOwnerNonceContext;
import com.sunsea.parkinghere.module.carowner.service.CarOwnerNonceService;

@Service
public class CarOwnerNonceServiceImpl implements CarOwnerNonceService {
    static final Log logger = LogFactory.getLog(CarOwnerNonceServiceImpl.class);
    
    private int nonceLifeTime = 30 * 60 * 1000;
    
    private ConcurrentHashMap<String, CarOwnerNonceContext> contextCache = new ConcurrentHashMap<String, CarOwnerNonceContext>();
    
    public CarOwnerNonceServiceImpl() {
        super();
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                logger.info("Start clean CarOwnerNonceContext.");
                if (logger.isDebugEnabled()) {
                    logger.debug("Before clean: CarOwnerNonceContext size=" + contextCache.size());
                }
                
                Iterator<Entry<String, CarOwnerNonceContext>> iter = contextCache.entrySet()
                                                                                 .iterator();
                while (iter.hasNext()) {
                    if (wasNonceExpired(iter.next().getValue())) {
                        iter.remove();
                    }
                }
                
                if (logger.isDebugEnabled()) {
                    logger.debug("After clean: CarOwnerNonceContext size=" + contextCache.size());
                }
            }
            
        },
                       30 * 60 * 1000,
                       30 * 60 * 1000);
    }
    
    @Override
    public CarOwnerNonceContext generateContext(String userId) {
        CarOwnerNonceContext context = getContextByUsername(userId);
        if (null == context) {
            context = new CarOwnerNonceContext();
            context.setNonce(UUID.randomUUID().toString());
            context.setUsername(userId);
            contextCache.put(context.getNonce(), context);
        }
        
        return context;
    }
    
    @Override
    public CarOwnerNonceContext generateContext(String userId,
                                                double locationX,
                                                double locationY,
                                                String locationLabel) {
        CarOwnerNonceContext context = getContextByUsername(userId);
        if (null == context) {
            context = new CarOwnerNonceContext();
            context.setNonce(UUID.randomUUID().toString());
            context.setUsername(userId);
            contextCache.put(context.getNonce(), context);
        }
        
        if (CarOwnerDebugerContoller.testUsername.equals(userId)) {
            // for debuger
            context.setUserLocationX(locationX);
            context.setUserLocationY(locationY);
        }
        else {
            Point baiduPoint = CoordinateUtils.convert(new Point(locationY,
                                                                 locationX),
                                                       CoordinateType.GOOGLE_LL,
                                                       CoordinateType.BAIDU_LL);
            
            context.setUserLocationX(baiduPoint.getX());
            context.setUserLocationY(baiduPoint.getY());
        }
        context.setUserLocationLabel(locationLabel);
        return context;
    }
    
    protected boolean wasNonceExpired(CarOwnerNonceContext nonceContext) {
        return nonceContext.getModifyAt() + nonceLifeTime < System.currentTimeMillis();
    }
    
    @Override
    public CarOwnerNonceContext getContext(String nonce) {
        CarOwnerNonceContext context = contextCache.get(nonce);
        if (null != context && !wasNonceExpired(context)) {
            return context;
        }
        return null;
    }
    
    public int getNonceLifeTime() {
        return nonceLifeTime;
    }
    
    public void setNonceLifeTime(int nonceLifeTime) {
        this.nonceLifeTime = nonceLifeTime;
    }
    
    @Override
    public CarOwnerNonceContext getContextByUsername(String username) {
        for (CarOwnerNonceContext context : contextCache.values()) {
            if (username.equals(context.getUsername())) {
                context.setModifyAt(System.currentTimeMillis());
                return context;
            }
        }
        return null;
    }

    @Override
    public void cleanContextByUsername(String username) {
        contextCache.remove(username);
    }
}
