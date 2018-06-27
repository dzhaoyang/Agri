package com.sunsea.parkinghere.biz.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.repository.custom.GroupRepositoryCustom;

@Repository
public interface GroupRepository extends
                                AbstractRepository<Group>,
                                GroupRepositoryCustom {
    
    public Group findByName(String name);
    
    public List<Group> findByNameLike(String name);
    
}
