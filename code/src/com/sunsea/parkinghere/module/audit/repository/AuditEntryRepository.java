package com.sunsea.parkinghere.module.audit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.repository.AbstractRepository;
import com.sunsea.parkinghere.module.audit.model.AuditEntry;
import com.sunsea.parkinghere.module.audit.repository.custom.AuditEntryRepositoryCustom;

@Repository
public interface AuditEntryRepository extends
                                     AbstractRepository<AuditEntry>,
                                     AuditEntryRepositoryCustom {
    
    public Page<AuditEntry> findByRequestBy(String requestBy, Pageable pageable);
    
}
