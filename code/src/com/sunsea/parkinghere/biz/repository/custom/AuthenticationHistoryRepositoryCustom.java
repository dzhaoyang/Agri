package com.sunsea.parkinghere.biz.repository.custom;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.repository.parameter.AuthenticationHistoryQueryParameter;

public interface AuthenticationHistoryRepositoryCustom {
    
    public Page<AuthenticationHistory> find(AuthenticationHistoryQueryParameter parameter);
    
    public void removeByDateRange(Date beginDate, Date endDate);

}
