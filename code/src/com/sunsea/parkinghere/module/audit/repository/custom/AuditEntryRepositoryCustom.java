package com.sunsea.parkinghere.module.audit.repository.custom;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.sunsea.parkinghere.module.audit.model.AuditEntry;
import com.sunsea.parkinghere.module.audit.openapi.AuditEntryQueryParameter;

public interface AuditEntryRepositoryCustom {
    
    public Page<AuditEntry> find(AuditEntryQueryParameter parameter);
    
    public void removeByDateRange(Date beginDate, Date endDate);
    
}
