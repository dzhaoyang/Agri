package com.sunsea.parkinghere.biz.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunsea.parkinghere.biz.model.Role;

public class MemCachedRoles {
    
    private Map<String, Role> idRoleRepository = new HashMap<String, Role>();
    
    private Map<String, Role> nameRolerepository = new HashMap<String, Role>();
    
    private List<Role> roles = new ArrayList<Role>();
    
    public MemCachedRoles(List<Role> roles) {
        this.roles.addAll(roles);
        for (Role role : roles) {
            idRoleRepository.put(role.getId(), role);
            nameRolerepository.put(role.getName(), role);
        }
    }
    
    public List<Role> findAll() {
        return Collections.unmodifiableList(roles);
    }
    
    public Role findById(String id) {
        return idRoleRepository.get(id);
    }
    
    public Role findByName(String id) {
        return nameRolerepository.get(id);
    }
    
}
