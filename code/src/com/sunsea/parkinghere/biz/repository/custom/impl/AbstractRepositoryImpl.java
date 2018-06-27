package com.sunsea.parkinghere.biz.repository.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.repository.custom.AbstractRepositoryCustom;

@Service
public class AbstractRepositoryImpl implements AbstractRepositoryCustom {
    
    @Autowired
    protected MongoTemplate mongoTemplate;
    
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
    
}
