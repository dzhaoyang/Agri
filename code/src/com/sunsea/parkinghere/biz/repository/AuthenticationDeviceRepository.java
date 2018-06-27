package com.sunsea.parkinghere.biz.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.model.AuthenticationDevice;
import com.sunsea.parkinghere.biz.model.User;

@Repository
public interface AuthenticationDeviceRepository extends
                                               AbstractRepository<AuthenticationDevice> {
    
    public AuthenticationDevice findByDeviceId(String deviceId);
    
    public List<AuthenticationDevice> findByUser(User user);
    
}
