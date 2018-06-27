package com.sunsea.parkinghere.module.carowner.service;

import com.sunsea.parkinghere.module.carowner.model.CarOwnerNonceContext;

public interface CarOwnerNonceService {
    public CarOwnerNonceContext generateContext(String userId);
    
    public CarOwnerNonceContext generateContext(String userId,
                                                double locationX,
                                                double locationY,
                                                String locationLabel);
    
    public CarOwnerNonceContext getContext(String nonce);
    
    public CarOwnerNonceContext getContextByUsername(String username);
    
    public void cleanContextByUsername(String username);
}
