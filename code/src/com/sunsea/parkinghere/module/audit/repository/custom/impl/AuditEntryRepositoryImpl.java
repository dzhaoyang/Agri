package com.sunsea.parkinghere.module.audit.repository.custom.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.repository.custom.impl.AbstractRepositoryImpl;
import com.sunsea.parkinghere.module.audit.model.AuditEntry;
import com.sunsea.parkinghere.module.audit.openapi.AuditEntryQueryParameter;
import com.sunsea.parkinghere.module.audit.repository.custom.AuditEntryRepositoryCustom;

@Service
public class AuditEntryRepositoryImpl extends AbstractRepositoryImpl implements
                                                                    AuditEntryRepositoryCustom {
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    @Override
    public Page<AuditEntry> find(AuditEntryQueryParameter parameter) {
        int start = AuditEntryQueryParameter.getStart(parameter);
        int limit = AuditEntryQueryParameter.getLimit(parameter);
        
        Query query = new Query();
        if (StringUtils.isNotEmpty(parameter.getRequestBy())) {
            query.addCriteria(Criteria.where("requestBy")
                                      .is(parameter.getRequestBy()));
        }
        
        if (StringUtils.isNotEmpty(parameter.getClientAddress())) {
            query.addCriteria(Criteria.where("ClientAddress")
                                      .is(parameter.getClientAddress()));
        }
        
        if (StringUtils.isNotEmpty(parameter.getStatus())) {
            query.addCriteria(Criteria.where("status")
                                      .is(parameter.getStatus()));
        }
        
        if (StringUtils.isNotEmpty(parameter.getRequestedUrl())) {
            query.addCriteria(Criteria.where("requestedUrl")
                                      .regex(parameter.getRequestedUrl(), "i"));
        }
        
        if (null != parameter.getRequestAt()) {
            Date tomorrow = new Date(parameter.getRequestAt().getTime() + (1000 * 60 * 60 * 24));
            query.addCriteria(Criteria.where("requestAt")
                                      .gt(parameter.getRequestAt())
                                      .lt(tomorrow));
        }
        
        PageRequest pageable = new PageRequest(start, limit);
        query.with(pageable).with(new Sort(new Order(Direction.DESC,
                                                     "requestAt")));
        
        long count = mongoTemplate.count(query, AuditEntry.class);
        List<AuditEntry> list = mongoTemplate.find(query, AuditEntry.class);
        return new PageImpl<AuditEntry>(list, pageable, count);
    }
    
    @Override
    public void removeByDateRange(Date beginDate, Date endDate) {
        if (beginDate == null) {
            throw new IllegalArgumentException("The begin date can't be null!");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("The end date can't be null!");
        }
        mongoTemplate.remove(new Query().addCriteria(Criteria.where("requestAt")
                                                             .gte(beginDate)
                                                             .lte(endDate)),
                             AuditEntry.class);
    }
    
}
