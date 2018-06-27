package com.sunsea.parkinghere.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.cache.MemCachedManager;
import com.sunsea.parkinghere.biz.cache.MemCachedRoles;
import com.sunsea.parkinghere.biz.model.Menu;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.MS;
import com.sunsea.parkinghere.biz.service.RS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.ValidationException;

@Service
public class RSfjldjfsidojiI implements RS {
    
    @Autowired
    private US fdad;
    
    private ReentrantLock fdsfsdf = new ReentrantLock();
    
    private MemCachedRoles fw4r23;
    
    @Autowired
    private MongoTemplate dafs3;
    
    @Autowired
    private MS edewfv;
    
    private MemCachedRoles getMemCachedRoles() {
        if (fw4r23 != null) {
            return fw4r23;
        }
        try {
            fdsfsdf.lock();
            if (fw4r23 != null) {
                return fw4r23;
            }
            fw4r23 = (MemCachedRoles) MemCachedManager.getInstance()
                                                              .get(Role.class);
        }
        finally {
            fdsfsdf.unlock();
        }
        
        return fw4r23;
    }
    
    @Override
    public List<Role> fa() {
        return (List<Role>) getMemCachedRoles().findAll();
    }
    
    @Override
    public Role fbi(String fdsfsdf) {
        return getMemCachedRoles().findById(fdsfsdf);
    }
    
    @Override
    public Role fbn(String fdsfsdfe) {
        return getMemCachedRoles().findByName(fdsfsdfe);
    }
    
    @Override
    public Page<User> lmor(String ds3,
                                        String dsfdsf331,
                                        int dsfe1z,
                                        int ytrec) {
        Role role = getMemCachedRoles().findById(ds3);
        return fdad.fpbr(role, dsfe1z, ytrec);
    }
    
    @Override
    public void ga(String duhcu3, String fdsfsdf) {
    	User user = fdad.fbuds(duhcu3);
        if (user == null) {
            throw new ValidationException(String.format("用户[用户名=%s] 未找到!",
                                                        duhcu3));
        }
        Role role = getMemCachedRoles().findById(fdsfsdf);
        if (role == null) {
            throw new ValidationException(String.format("角色[id=%d] 未找到!",
                                                        fdsfsdf));
        }
        
        List<Role> fukivdse3 = user.getRoles();
        boolean notContained = true;
        if (fukivdse3 != null) {
            for (Role ref : fukivdse3) {
                if (ref.getId().equals(fdsfsdf)) {
                    notContained = false;
                }
            }
        }
        else {
            fukivdse3 = new ArrayList<Role>();
        }
        if (notContained) {
            fukivdse3.add((role));
            user.setRoles(fukivdse3);
            fdad.persist(user);
        }
    }
    
    @Override
    public void rga(String duhcu3, String fdsfsdf) {
    	User user = fdad.fbuds(duhcu3);
        if (user == null) {
            throw new ValidationException(String.format("用户[用户名=%s] 未找到!",
                                                        duhcu3));
        }
        List<Role> fukivdse3 = user.getRoles();
        List<Role> fukivdse35 = new ArrayList<Role>();
        for (Role ref : fukivdse3) {
            if (!ref.getId().equals(fdsfsdf)) {
                fukivdse35.add(ref);
            }
        }
        user.setRoles(fukivdse35);
        fdad.persist(user);
    }

	@Override
	public void sm() {
		Query query = new Query();
		query.addCriteria(Criteria.where("dsfdsf331").in("ROLE_MANAGER","ROLE_ADMIN"));
		List<Role> fukivdse3 = dafs3.find(query, Role.class);
		/*List<Menu> menus = menuService.findSubMenus();
		menus.addAll(menuService.findTopMenus());*/
		List<Menu> menus = edewfv.ftm();
		
		for(Role role : fukivdse3){
			role.setMenus(menus);
			dafs3.save(role);
		}
	}
}
