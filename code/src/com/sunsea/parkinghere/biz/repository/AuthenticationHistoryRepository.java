package com.sunsea.parkinghere.biz.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.custom.AuthenticationHistoryRepositoryCustom;

@Repository
public interface AuthenticationHistoryRepository extends
                                                AbstractRepository<AuthenticationHistory>,
                                                AuthenticationHistoryRepositoryCustom {
    
    public Page<AuthenticationHistory> findByUser(User user, Pageable pageable);
    
    public Page<AuthenticationHistory> findByLoginAt(Date date,
                                                     Pageable pageable);
    
}
