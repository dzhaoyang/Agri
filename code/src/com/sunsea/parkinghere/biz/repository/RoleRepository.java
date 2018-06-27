package com.sunsea.parkinghere.biz.repository;

import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.repository.custom.RoleRepositoryCustom;

@Repository
public interface RoleRepository extends
                               AbstractRepository<Role>,
                               RoleRepositoryCustom {
    
    public Role findByName(String name);
    
}
