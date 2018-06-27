package com.sunsea.parkinghere.biz.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.Roles;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.UserRepository;
import com.sunsea.parkinghere.biz.repository.parameter.UserQueryParameter;
import com.sunsea.parkinghere.biz.service.GS;
import com.sunsea.parkinghere.biz.service.RS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.ValidationException;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Auditable
@Controller
@RequestMapping("/api/v1/dashboard/identity")
public class URSfdjsifwjiopew {
    
    @Autowired
    private US ggfgfdgf;
    
    @Autowired
    private UserRepository fdfdsfsd;
    
    @Autowired
    private RS fdsfdsfsd;
    
    @Autowired
    private GS fsdfsfsdfs;
    
    @Auditable
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public Object lu(UserQueryParameter p) {
    	try{
    		p.setEmail("");
    		p.setGroupId("");
    		Page<User> users = null;
            if (StringUtils.isNotEmpty(p.getRoleId()) && StringUtils.isNotEmpty(p.getGroupId())) {
                users = fdfdsfsd.findByParamsWithGroupsAndRoles(p.getUsername(),p.getEmail(),
                        											  p.getName(),p.getPhoneNumber(),
                        											  p.getRoleId(),p.getGroupId(),
                        											  new PageRequest(p.getStart(),p.getLimit()));
            }
            else if (StringUtils.isEmpty(p.getRoleId()) && StringUtils.isNotEmpty(p.getGroupId())) {
                users = fdfdsfsd.findByParamsWithGroups(p.getUsername(),p.getEmail(),
                        									  p.getName(),p.getPhoneNumber(),
                        									  p.getGroupId(),
                        									  new PageRequest(p.getStart(),p.getLimit()));
            }
            else if (StringUtils.isNotEmpty(p.getRoleId()) && StringUtils.isEmpty(p.getGroupId())) {
                users = fdfdsfsd.findByParamsWithRoles(p.getUsername(), p.getEmail(),
                        									 p.getName(), p.getPhoneNumber(),
                        									 p.getRoleId(),
                        									 new PageRequest(p.getStart(), p.getLimit()));
            }
            else {
            	users = fdfdsfsd.findByParams(p.getUsername(), p.getEmail(),
                        							p.getName(), p.getPhoneNumber(),
                        							new PageRequest(p.getStart(), p.getLimit()));
                
            }
            if(users!=null&&users.getContent()!=null&&!users.getContent().isEmpty()){
            	for(User user : users.getContent()){
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
            	}
            }
            return WebUtils.succeedMap(users);
    	}catch(Exception e){
    		e.printStackTrace();
    		return WebUtils.failedMap(e.getMessage());
    	}
    	
    }
    
    @Auditable
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public Object cu(User we,
			    		String[] groupIds,
			            String[] roleIds,
			            String roletype,
			            ModelMap map,
			            HttpServletRequest request,
			            HttpSession session) throws Exception {
        String password1 = request.getParameter("password1");
        String manager = request.getParameter("manager");
        String enabled = request.getParameter("enabled");
        // String email = request.getParameter("email");
        we.setEnabled("true".equals(enabled));
        
        if (StringUtils.isEmpty(we.getId())) {
            if (StringUtils.isEmpty(we.getUsername())) {
                throw new ValidationException("错误的请求: 用户名为空!");
            }
            
            if (User.USERNAME_ADMINISTRATOR.equalsIgnoreCase(we.getUsername())) {
                throw new ValidationException("错误的请求: 用户名不能为 administrator!");
            }
            
            if (!we.getPassword().equals(password1)) {
                throw new ValidationException("错误的请求: 2次输入的密码不一致!");
            }
            ggfgfdgf.validateUser(we);
            
            List<Role> roles = new ArrayList<Role>();
            roles.add((fdsfdsfsd.fbn(Roles.ROLE_USER)));
            
            if("1".equals(roletype)){
            	Role role = fdsfdsfsd.fbn(Roles.ROLE_MANAGER);
                roles.add((role));
            }
            we.setRoles(roles);
            
            List<Group> groups = new ArrayList<Group>();
            if (groupIds != null) {
                for (int i = 0; i < groupIds.length; i++) {
                    Group group = fsdfsfsdfs.findById(groupIds[i]);
                    groups.add((group));
                }
            }
            we.setGroups(groups);
            ggfgfdgf.ca(we);
            return WebUtils.succeedMap(we);
        }
        else {
        	User target = ggfgfdgf.findById(we.getId());
            if (target == null) {
                throw new ValidationException(String.format("用户[id=%s] 未找到!",
                                                            we.getId()));
            }
            // userService.validateUser(user);
            target.setEmail(we.getEmail());
            target.setName(we.getName());
            target.setDisplayName(we.getDisplayName());
            target.setTitle(we.getTitle());
            target.setEnabled(we.isEnabled());
            target.setPhoneNumber(we.getPhoneNumber());
            
            if (User.USERNAME_ADMINISTRATOR.equalsIgnoreCase(target.getUsername())) {
                List<Role> rolesOfAdmin = new ArrayList<Role>();
                rolesOfAdmin.add((fdsfdsfsd.fbn(Roles.ROLE_USER)));
                /*rolesOfAdmin.add((roleService.findByName(Roles.ROLE_MANAGER)));*/
                rolesOfAdmin.add((fdsfdsfsd.fbn(Roles.ROLE_ADMIN)));
                target.setRoles(rolesOfAdmin);
            }
            else {
                List<Role> roles = new ArrayList<Role>();
                roles.add((fdsfdsfsd.fbn(Roles.ROLE_USER)));
               
                if("1".equals(roletype)){
                	Role role = fdsfdsfsd.fbn(Roles.ROLE_MANAGER);
                    roles.add((role));
                }
                target.setRoles(roles);
            }
            
            List<Group> groups = new ArrayList<Group>();
            if (groupIds != null) {
                for (int i = 0; i < groupIds.length; i++) {
                    Group group = fsdfsfsdfs.findById(groupIds[i]);
                    groups.add((group));
                }
            }
            target.setGroups(groups);
            
            ggfgfdgf.persist(target);
            return WebUtils.succeedMap(target);
        }
    }
    
    @Auditable
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object fdsfdsfs(@PathVariable("id") String gfgd,
    						 User ferrtrtr3,
    						 String[] groupIds,
                             String[] roleIds,
                             String roletype,
                             String password1,
                             String manager,
                             String enabled) throws Exception {
    	try{
    		ferrtrtr3.setEnabled("true".equals(enabled));
            
            if (StringUtils.isEmpty(ferrtrtr3.getId())) {
                if (StringUtils.isEmpty(ferrtrtr3.getUsername())) {
                    throw new ValidationException("请求错误: 用户名为空!");
                }
                
                if (User.USERNAME_ADMINISTRATOR.equalsIgnoreCase(ferrtrtr3.getUsername())) {
                    throw new ValidationException("错误的请求: 用户名不能为 admin!");
                }
                
                if (!ferrtrtr3.getPassword().equals(password1)) {
                    throw new ValidationException("错误的请求: 2次输入的密码不一致!");
                }
                ggfgfdgf.validateUser(ferrtrtr3);
                
                List<Role> roles = new ArrayList<Role>();
                roles.add((fdsfdsfsd.fbn(Roles.ROLE_USER)));
                
                if("1".equals(roletype)){
                	Role role = fdsfdsfsd.fbn(Roles.ROLE_MANAGER);
                    roles.add((role));
                }
                ferrtrtr3.setRoles(roles);
                
                List<Group> groups = new ArrayList<Group>();
                if (groupIds != null) {
                    for (int i = 0; i < groupIds.length; i++) {
                        Group group = fsdfsfsdfs.findById(groupIds[i]);
                        groups.add((group));
                    }
                }
                ferrtrtr3.setGroups(groups);
                ggfgfdgf.ca(ferrtrtr3);
                return WebUtils.succeedMap(ferrtrtr3);
            }
            else {
            	User target = ggfgfdgf.findById(ferrtrtr3.getId());
                if (target == null) {
                    throw new ValidationException(String.format("用户[id=%s] 未找到!",
                                                                ferrtrtr3.getId()));
                }
                target.setEmail(ferrtrtr3.getEmail());
                target.setName(ferrtrtr3.getName());
                target.setDisplayName(ferrtrtr3.getDisplayName());
                target.setTitle(ferrtrtr3.getTitle());
                target.setEnabled(ferrtrtr3.isEnabled());
                target.setPhoneNumber(ferrtrtr3.getPhoneNumber());
                target.setDepartment(ferrtrtr3.getDepartment());
                if (User.USERNAME_ADMINISTRATOR.equalsIgnoreCase(target.getUsername())) {
                    List<Role> rolesOfAdmin = new ArrayList<Role>();
                    rolesOfAdmin.add((fdsfdsfsd.fbn(Roles.ROLE_USER)));
                    rolesOfAdmin.add((fdsfdsfsd.fbn(Roles.ROLE_ADMIN)));
                    target.setRoles(rolesOfAdmin);
                }
                else {
                    List<Role> roles = new ArrayList<Role>();
                    roles.add((fdsfdsfsd.fbn(Roles.ROLE_USER)));
                  
                    if("1".equals(roletype)){
                    	Role role = fdsfdsfsd.fbn(Roles.ROLE_MANAGER);
                        roles.add((role));
                    }
                    target.setRoles(roles);
                }
                
                List<Group> groups = new ArrayList<Group>();
                if (groupIds != null) {
                    for (int i = 0; i < groupIds.length; i++) {
                        Group group = fsdfsfsdfs.findById(groupIds[i]);
                        groups.add((group));
                    }
                }
                target.setGroups(groups);
                
                ggfgfdgf.persist(target);
                return WebUtils.succeedMap(target);
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		return WebUtils.failedMap(e.getMessage());
    	}
        
    }
    
    @Auditable
    @RequestMapping(value = "/users/{id}/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map cp(@PathVariable("id") String dfds,
    		String newPassword,
            String newPassword1) {
    	User user = ggfgfdgf.findById(dfds);
        if (user == null) {
            throw new RuntimeException(String.format("错误的请求:用户[id=%s] 未找到!", dfds));
        }
        if (!newPassword.equals(newPassword1)) {
            throw new RuntimeException("错误的请求: 2次输入的密码不一致");
        }
        
        ggfgfdgf.cp(dfds, newPassword);
        return WebUtils.succeedMap();
    }
    
    @Auditable
    @RequestMapping(value = "/users/{id}/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Map fdsfdsfdsfds(@PathVariable("id") String fdsfd, ModelMap map) {
    	User user = ggfgfdgf.findById(fdsfd);
        if (user == null) {
            throw new RuntimeException(String.format("错误的请求:用户[id=%s] 未找到!", fdsfd));
        }
        
        ggfgfdgf.cpcr(user);
        return WebUtils.succeedMap();
    }
    
    @Auditable
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object du(@PathVariable("id") String fdsf) throws Exception {
        if (StringUtils.isEmpty(fdsf)) {
            return WebUtils.failedMap("错误的请求:用户id为空!");
        }
        
        User user = ggfgfdgf.findById(fdsf);
        if (null != user) {
            ggfgfdgf.rfdsd(user);
        }
        return WebUtils.succeedMap(null);
    }
    
}
