package com.sunsea.parkinghere.biz.repository.custom.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.repository.custom.AuthenticationHistoryRepositoryCustom;
import com.sunsea.parkinghere.biz.repository.parameter.AuthenticationHistoryQueryParameter;

@Service
public class AuthenticationHistoryRepositoryImpl extends AbstractRepositoryImpl implements
                                                                               AuthenticationHistoryRepositoryCustom {
    
    @Override
    public Page<AuthenticationHistory> find(AuthenticationHistoryQueryParameter parameter) {
        int start = AuthenticationHistoryQueryParameter.getStart(parameter);
        int limit = AuthenticationHistoryQueryParameter.getLimit(parameter);
        
        Query query = new Query();
        if (StringUtils.isNotBlank(parameter.getAuthType())) {
            query.addCriteria(Criteria.where("authType")
                                      .is(parameter.getAuthType()));
        }
        
        if (StringUtils.isNotBlank(parameter.getIpAddress())) {
            query.addCriteria(Criteria.where("ipAddress")
                                      .is(parameter.getIpAddress()));
        }
        
        if (StringUtils.isNotBlank(parameter.getUsername())) {
            query.addCriteria(Criteria.where("username")
                                      .is(parameter.getUsername()));
        }
        
        if (parameter.getLoginAt() != null) {
            Date tomorrow = new Date(parameter.getLoginAt().getTime() + (1000 * 60 * 60 * 24));
            query.addCriteria(Criteria.where("loginAt")
                                      .gt(parameter.getLoginAt())
                                      .lt(tomorrow));
        }
        
        PageRequest pageable = new PageRequest(start, limit);
        query.with(pageable)
             .with(new Sort(new Order(Direction.DESC, "loginAt")));
        
        long count = mongoTemplate.count(query, AuthenticationHistory.class);
        List<AuthenticationHistory> list = mongoTemplate.find(query,
                                                              AuthenticationHistory.class);
        return new PageImpl<AuthenticationHistory>(list, pageable, count);
    }
    
    @Override
    public void removeByDateRange(Date beginDate, Date endDate) {
        if (beginDate == null) {
            throw new IllegalArgumentException("The begin date can't be null!");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("The end date can't be null!");
        }
        
        if (beginDate.after(endDate)) {
            throw new IllegalArgumentException("The end date is before the begin date!");
        }
        
        endDate = DateUtils.addDays(endDate, 1);
        
        mongoTemplate.remove(new Query().addCriteria(Criteria.where("loginAt")
                                                             .gte(beginDate)
                                                             .lt(endDate)),
                             AuthenticationHistory.class);
    }
    
}
