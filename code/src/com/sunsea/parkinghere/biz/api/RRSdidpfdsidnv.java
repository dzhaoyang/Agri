package com.sunsea.parkinghere.biz.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.repository.RoleRepository;
import com.sunsea.parkinghere.biz.repository.UserRepository;
import com.sunsea.parkinghere.biz.service.RS;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;


@Controller
@RequestMapping("/api/v1/dashboard/identity")
public class RRSdidpfdsidnv {
    
    @Autowired
    private RoleRepository fdsfadfd;
    
    @Autowired
    private UserRepository fdsfdsfsdfdfdsf2;

    @Autowired
	private RS fdsfsdfsdfsdfds;
    
    @Auditable
    @RequestMapping("/roles")
    @ResponseBody
    public Object fdsffdvgnjjubvdsff55grvds(ModelMap map) {
        return WebUtils.succeedMap(fdsfadfd.findAll());
    }
    
    @Auditable
    @RequestMapping("/roles/{id}/members")
    @ResponseBody
    public Object uythgfdsbvcdsafdsew32d(@PathVariable("id") String dfsdfdsfc,
                                    String name,
                                    int start,
                                    int limit,
                                    ModelMap map) throws Exception {
        Role role = fdsfadfd.findOne(dfsdfdsfc);
        if (StringUtils.isEmpty(name)) {
            return WebUtils.succeedMap(fdsfdsfsdfdfdsf2.findByRoles(role,
                                                                  new PageRequest(start,
                                                                		  limit)));
        }
        else {
            return WebUtils.succeedMap(fdsfdsfsdfdfdsf2.findByRolesAndUsernameLike(role,
            		name,
                                                                                 new PageRequest(start,
                                                                                		 limit)));
        }
        
    }
    
    
    @RequestMapping("/setManagerRole")
    @ResponseBody
    public Object hgfdsdyuytrds() {
    	try{
    		fdsfsdfsdfsdfds.sm();
			return WebUtils.succeedMap();
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
    }
    
}
