package com.sunsea.parkinghere.biz.repository;

import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.model.ApplicationSetting;

@Repository
public interface ApplicationSettingRepository extends
                                             AbstractRepository<ApplicationSetting> {
    
    public ApplicationSetting findByKey(String key);
    
}
