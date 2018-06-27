package com.sunsea.parkinghere.biz.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.Roles;
import com.sunsea.parkinghere.biz.service.GS;
import com.sunsea.parkinghere.biz.service.RS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.ValidationException;

@Controller
@RequestMapping(value = "/dashboard/identity", method = RequestMethod.GET)
public class UMC {
    
    @Autowired
    private US wertyuhgfds;
    
    @Autowired
    private GS sdfghjytrewsv;
    
    @Autowired
    private RS wertyjhgqertyujh;
    
    @RequestMapping(value = "/users")
    public String fdggfdg435gfdvdc(ModelMap map, HttpSession session) {
        
        List<Role> roles = wertyjhgqertyujh.fa();
        map.put("roles", roles);
        
        List<Group> groups = sdfghjytrewsv.findAll();
        map.put("groups", groups);
        return "user/list";
    }
    
    private List<Role> fdsfdst435(List<Role> dssads) {
        List<Role> result = new ArrayList<Role>();
        for (Role role : dssads) {
            if (role.getName().equals("ROLE_MANAGER") || role.getName().equals("ROLE_SALESMAN")) {
                result.add(role);
            }
        }
        return result;
    }
    
    @RequestMapping(value = "/users/new")
    public String iuyikjndf43rfd(ModelMap map) throws Exception {
    	User user = new User();
    	user.setRoleName("普通员工");
        map.put("user", user);
        
        List<Role> roles = wertyjhgqertyujh.fa();
        map.put("roles", fdsfdst435(roles));
        
        List<Group> groups = sdfghjytrewsv.findAll();
        map.put("groups", groups);
        return "user/edit";
    }
    
    @RequestMapping(value = "/users/{id}/edit")
    public String etrytijhgsr5(@PathVariable("id") String id, ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        List<String> list = new ArrayList<String>();
		for(Role role : user.getRoles()){
			list.add(role.getName());
		}
		if(list.contains("ROLE_ADMIN")){
			user.setRoleName("系统管理员");
		}else if(list.contains("ROLE_MANAGER")){
			user.setRoleName("领导");
		}else if(list.contains("ROLE_USER")){
			user.setRoleName("普通员工");
		}
        map.put("user", user);
        
        List<Group> userGroups = new ArrayList<Group>();
        List<Group> userGroupRefs = user.getGroups();
        if (userGroupRefs != null) {
            for (Group ref : userGroupRefs) {
                userGroups.add(ref);
            }
        }
        map.put("userGroups", userGroups);
        
        List<Group> groups = sdfghjytrewsv.findAll();
        map.put("groups", groups);
        
        List<Role> userRoles = new ArrayList<Role>();
        List<Role> userRoleRefs = user.getRoles();
        if (userRoleRefs != null) {
            for (Role ref : userRoleRefs) {
                userRoles.add(ref);
            }
        }
        map.put("userRoles", userRoles);
        
        List<Role> roles = wertyjhgqertyujh.fa();
        map.put("roles", fdsfdst435(roles));
        
        
        
        return "user/edit";
    }
    
    @RequestMapping(value = "/users/{id}")
    public String wertyjhgf(@PathVariable("id") String id, ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        map.put("user", user);
        fdsft43tgfd(map, user);
        
        return "user/detail";
    }
    
    private void fdsft43tgfd(ModelMap map, User fdsfsdfdsf) {
        List<Group> userGroups = new ArrayList<Group>();
        List<Group> userGroupRefs = fdsfsdfdsf.getGroups();
        if (userGroupRefs != null) {
            for (Group ref : userGroupRefs) {
                userGroups.add(ref);
            }
        }
        map.put("groups", userGroups);
        
        List<Role> roles = new ArrayList<Role>();
        List<Role> roleRefs = fdsfsdfdsf.getRoles();
        if (roleRefs != null) {
            for (Role ref : roleRefs) {
                ref.setName(Roles.getInstance()
                                 .getFriendlyRoleName(ref.getName()));
                roles.add(ref);
            }
        }
        map.put("roles", roles);
    }
    
    @RequestMapping(value = "/users/{id}/profile/index")
    public String dsfdsaferfvsdfdwq3(@PathVariable("id") String id,
                                           ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        map.put("user", user);
        fdsft43tgfd(map, user);
        
        return "user/profile/index";
    }
    
    @RequestMapping(value = "/users/{id}/profile/audit")
    public String fdgfgf53gf745(@PathVariable("id") String id,
                                       ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        map.put("user", user);
        
        return "user/profile/audit";
    }
    
    @RequestMapping(value = "/users/{id}/profile/authHistory")
    public String ddsfd5gft3454(@PathVariable("id") String id,
                                             ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        map.put("user", user);
        
        return "user/profile/authHistory";
    }
    
    @RequestMapping(value = "/users/{id}/profile/parking")
    public String fdsfdsf3432tgfd(@PathVariable("id") String id,
                                            ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        map.put("user", user);
        
        return "user/profile/parking";
    }
    
    @RequestMapping(value = "/users/{id}/profile/apprisal")
    public String fdsfdgf4534gfd(@PathVariable("id") String id,
                                          ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        map.put("user", user);
        
        return "user/profile/apprisal";
    }
    
    @RequestMapping(value = "/users/{id}/profile/booking")
    public String fdsfsdfds423tgfv(@PathVariable("id") String id,
                                            ModelMap map) throws Exception {
    	User user = wertyuhgfds.findById(id);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!", id));
        }
        map.put("user", user);
        
        return "user/profile/booking";
    }
    
}
