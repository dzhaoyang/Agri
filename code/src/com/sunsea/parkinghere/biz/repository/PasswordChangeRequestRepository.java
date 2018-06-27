package com.sunsea.parkinghere.biz.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.model.PasswordChangeRequest;
import com.sunsea.parkinghere.biz.repository.custom.PasswordChangeRequestRepositoryCustom;

@Repository
public interface PasswordChangeRequestRepository extends
                                                AbstractRepository<PasswordChangeRequest>,
                                                PasswordChangeRequestRepositoryCustom {
    
    public PasswordChangeRequest findByRequestId(String requestId);
    
    public List<PasswordChangeRequest> findByUser(User user);
    
}
