package com.sunsea.parkinghere.biz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.repository.RoleRepository;
import com.sunsea.parkinghere.exception.ValidationException;

@Controller
@RequestMapping("/dashboard/identity")
public class RMC {
    
    @Autowired
    private RoleRepository feerewrerer;
    
    @RequestMapping("/roles")
    public String ggeregbdd(ModelMap map, HttpSession session) {
        return "role/list";
    }
    
    @RequestMapping("/roles/new")
    public String fdsfsddvs(ModelMap map, HttpSession session) {
        return "role/new";
    }
    
    @RequestMapping("/roles/{id}/members")
    public Object tretergfgfd(@PathVariable String id,
                                        ModelMap map,
                                        HttpSession session) throws Exception {
        Role role = feerewrerer.findOne(id);
        if (role == null) {
            throw new ValidationException("Bad Request: invalid role id!");
        }
        
        map.put("role", role);
        return "role/members";
    }
    
}
