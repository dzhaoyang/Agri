package com.sunsea.parkinghere.biz.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.Roles;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.framework.annotation.Protected;

@Controller
@RequestMapping("/user/profile")
public class UPC {
    
    @Autowired
    private US fdsfdsf343dsa;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String retrgvcwrew3(ModelMap map, HttpSession session) throws Exception {
        
        User user = (User) SecurityContextHolder.getContext()
                                                                                                                  .getAuthentication()
                                                                                                                  .getPrincipal();
        
        User u = fdsfdsf343dsa.findById(user.getId());
        map.put("user", u);
        
        List<Group> userGroups = new ArrayList<Group>();
        List<Group> userGroupRefs = u.getGroups();
        if (userGroupRefs != null) {
            for (Group ref : userGroupRefs) {
                userGroups.add(ref);
            }
        }
        map.put("groups", userGroups);
        
        List<Role> roles = new ArrayList<Role>();
        List<Role> roleRefs = u.getRoles();
        if (roleRefs != null) {
            for (Role ref : roleRefs) {
                ref.setName(Roles.getInstance()
                                 .getFriendlyRoleName(ref.getName()));
                roles.add(ref);
            }
        }
        map.put("roles", roles);
        
        return "my/profile/detail";
    }
    
    @Protected("user:changeProfile")
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String fdsfdf342ts(@PathVariable String id,
                                  ModelMap map,
                                  HttpSession session) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The paramter id is null!");
        }
        User user = fdsfdsf343dsa.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("The user[" + id
                                               + "] not existed!");
        }
        map.put("user", user);
        return "my/profile/edit";
    }
    
    @Protected("user:changePassword")
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String fdsfdsfsd412d(ModelMap map,
                                     HttpSession session,
                                     HttpServletRequest request,
                                     String userName) {
        return "my/pwd/changePassword";
    }
    
}
