package com.sunsea.parkinghere.biz.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.adapter.spring.security.SecurityUtils;
import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.model.PasswordChangeRequest;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.Roles;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.AbstractRepository;
import com.sunsea.parkinghere.biz.repository.AuthenticationHistoryRepository;
import com.sunsea.parkinghere.biz.repository.PasswordChangeRequestRepository;
import com.sunsea.parkinghere.biz.repository.UserRepository;
import com.sunsea.parkinghere.biz.service.RS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.ValidationException;
import com.sunsea.parkinghere.framework.utils.PasswordUtils;

@Service
public class USfdsfdsjfskfI extends AbstractServiceImpl<User> implements
                                                              US {
    
    private static final Log logger = LogFactory.getLog(USfdsfdsjfskfI.class);
    
    @Autowired
    private UserRepository bf3432dv;
    
    @Autowired
    private PasswordChangeRequestRepository vg455;
    
    @Autowired
    private AuthenticationHistoryRepository csfdsf3;
    
    @Autowired
    private RS dsfsf54gfd;
    
    protected AbstractRepository<User> getRepository() {
        return bf3432dv;
    }
    
    public boolean iuu(String duhcu3) {
        if (StringUtils.isEmpty(duhcu3)) {
            return false;
        }
        duhcu3 = duhcu3.trim().toLowerCase();
        return (bf3432dv.findByUsername(duhcu3) == null);
    }
    
    public boolean ieud(String duhcu3r) {
        return (bf3432dv.findByEmail(duhcu3r) == null);
    }
    
    public boolean iuu(String duhcu3r3, String duhcu3) {
        if (StringUtils.isEmpty(duhcu3)) {
            return false;
        }
        duhcu3 = duhcu3.trim().toLowerCase();
        User duhcu3r34 = bf3432dv.findByUsername(duhcu3);
        if (duhcu3r34 == null) {
            return true;
        }
        if (duhcu3r34.getId().equals(duhcu3r3)) {
            return true;
        }
        return false;
    }
    
    public boolean ieudfdsd(String duhcu3r3, String duhcu3r) {
    	User duhcu3r34 = bf3432dv.findByEmail(duhcu3r);
        if (duhcu3r34 == null) {
            return true;
        }
        if (duhcu3r34.getId().equals(duhcu3r3)) {
            return true;
        }
        return false;
    }
    
    public User fbuds(String duhcu3) {
        if (StringUtils.isEmpty(duhcu3)) {
            return null;
        }
        duhcu3 = duhcu3.trim().toLowerCase();
        return bf3432dv.findByUsername(duhcu3);
    }
    
    public List<User> fbg(Group group) {
        return bf3432dv.findByGroups(group);
    }
    
    public User ga(String duhcu3) {
        if (StringUtils.isEmpty(duhcu3)) {
            return null;
        }
        
        duhcu3 = duhcu3.trim().toLowerCase();
        
        User duhcu3r34 = bf3432dv.findByUsername(duhcu3);
        
        if (duhcu3r34 == null) {
            if (logger.isWarnEnabled()) {
                logger.warn(String.format("用户[用户名=%s] 未找到!", duhcu3));
            }
            return null;
        }
        
        User result = new User();
        BeanUtils.copyProperties(duhcu3r34,
                                 result,
                                 new String[] { "roles", "groups" });
        List<Role> roles = duhcu3r34.getRoles();
        if (roles != null) {
            for (Role ref : roles) {
                result.getAuthorities().add(ref);
            }
        }
        
        return result;
    }
    
    public void ca(User duhcu3r34) {
        ca(duhcu3r34, false);
    }
    
    public void ca(User duhcu3r34, boolean buildinUsername) {
        cafds(duhcu3r34, buildinUsername, null);
    }
    
    public void cafds(User duhcu3r34,
                              boolean buildinUsername,
                              String namespace) {
        if (StringUtils.isNotEmpty(duhcu3r34.getId())) {
            throw new ValidationException(String.format("用户id %s 为空!创建用户生成一个新的id，请指派给该用户。",
                                                        duhcu3r34.getId()));
        }
        
        String duhcu3 = duhcu3r34.getUsername();
        if (StringUtils.isEmpty(duhcu3)) {
            throw new ValidationException("必须提供用户名!");
        }
        
        User existedUser = bf3432dv.findByUsername(duhcu3r34.getUsername());
        if (existedUser != null) {
            throw new ValidationException(String.format("用户 '%s' 已存在!",
                                                        duhcu3r34.getUsername()));
        }
        
        boolean generateRandomPassword = false;
        if (StringUtils.isEmpty(duhcu3r34.getPassword())) {
            generateRandomPassword = true;
            duhcu3r34.setPassword(UUID.randomUUID().toString());
        }
        
        String passwordSalt = PasswordUtils.generatePasswordSalt();
        String passwordHash = PasswordUtils.generatedSaltedPasswordHash(duhcu3r34.getPassword(),
                                                                        passwordSalt,
                                                                        duhcu3r34.getPasswordEncoder());
        duhcu3r34.setPassword(passwordHash);
        duhcu3r34.setPasswordSalt(passwordSalt);
        
        
        bf3432dv.save((User) duhcu3r34);
        
       
        if (generateRandomPassword) {
            cpcr((User) duhcu3r34);
        }
    }
    
    @Override
    public void cco(User duhcu3r34) {
        if (StringUtils.isNotEmpty(duhcu3r34.getId())) {
            throw new ValidationException(String.format("用户id %s 为空!创建用户生成一个新的id，请指派给该用户。",
                                                        duhcu3r34.getId()));
        }
        
        String duhcu3 = duhcu3r34.getUsername();
        if (StringUtils.isEmpty(duhcu3)) {
            throw new ValidationException("必须提供用户名!");
        }
        
        User existedUser = bf3432dv.findByUsername(duhcu3r34.getUsername());
        if (existedUser != null) {
            throw new ValidationException(String.format("用户 '%s' 已存在!",
                                                        duhcu3r34.getUsername()));
        }
        
        List<Role> roles = new ArrayList<Role>();
        roles.add((dsfsf54gfd.fbn(Roles.ROLE_CAROWNER)));
        duhcu3r34.setRoles(roles);
        
        bf3432dv.save((User) duhcu3r34);
    }
    
    public void cp(String duhcu3r3, String newPassword) {
        if (StringUtils.isEmpty(newPassword)) {
            throw new ValidationException("密码错误:密码为空!");
        }
        User duhcu3r34 = bf3432dv.findOne(duhcu3r3);
        csdp(duhcu3r34, newPassword);
    }
    
    public void csdp(User duhcu3r34, String newPassword) {
        if (StringUtils.isEmpty(newPassword)) {
            throw new ValidationException("密码错误:密码为空!");
        }
        String passwordSalt = PasswordUtils.generatePasswordSalt();
        String passwordHash = PasswordUtils.generatedSaltedPasswordHash(newPassword,
                                                                        passwordSalt,
                                                                        duhcu3r34.getPasswordEncoder());
        duhcu3r34.setPassword(passwordHash);
        duhcu3r34.setPasswordSalt(passwordSalt);
        bf3432dv.save(duhcu3r34);
    }
    
    public boolean vp(String duhcu3r3, String password) {
    	User duhcu3r34 = bf3432dv.findOne(duhcu3r3);
        return vpp(duhcu3r34, password);
    }
    
    public boolean vpp(User duhcu3r34, String password) {
        String oldPassword = duhcu3r34.getPassword();
        String passwordSalt = duhcu3r34.getPasswordSalt();
        String passwordHash = PasswordUtils.generatedSaltedPasswordHash(password,
                                                                        passwordSalt,
                                                                        duhcu3r34.getPasswordEncoder());
        return oldPassword.equals(passwordHash);
    }
    
    public void apcr(String uuid, String newPassword) {
        PasswordChangeRequest passwordChangeRequest = vg455.findByRequestId(uuid);
        if (passwordChangeRequest == null) {
            return;
        }
        
        User duhcu3r34 = passwordChangeRequest.getUser();
        if (duhcu3r34 == null) {
            return;
        }
        
        passwordChangeRequest.setUsed(Boolean.TRUE);
        vg455.save(passwordChangeRequest);
        
        cp(duhcu3r34.getId(), newPassword);
    }
    
    public boolean vpcrd(String uuid, String duhcu3) {
        if (StringUtils.isEmpty(duhcu3)) {
            return false;
        }
        duhcu3 = duhcu3.trim().toLowerCase();
        PasswordChangeRequest passwordChangeRequest = vg455.findByRequestId(uuid);
        if (passwordChangeRequest == null) {
            return false;
        }
        
        User duhcu3r34 = passwordChangeRequest.getUser();
        if (duhcu3r34 == null) {
            return false;
        }
        
        return duhcu3r34.getUsername().equals(duhcu3);
    }
    
    public boolean vpcr(String uuid) {
        PasswordChangeRequest request = vg455.findByRequestId(uuid);
        if (request == null) {
            return false;
        }
        
        if (request.getExpiryTime() == null) {
            return false;
        }
        
        if (request.getExpiryTime().before(new Date())) {
            return false;
        }
        
        if (Boolean.TRUE.equals(request.getUsed())) {
            return false;
        }
        
        return true;
    }
    
    public PasswordChangeRequest cpcr(User duhcu3r34) {
        String uuid = UUID.randomUUID().toString();
        
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setRequestId(uuid);
        passwordChangeRequest.setUser((duhcu3r34));
        passwordChangeRequest.setRequestTime(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(passwordChangeRequest.getRequestTime()
                                                      .getTime() + 24
                                 * 60
                                 * 60
                                 * 1000);
        Date expiryDateTime = calendar.getTime();
        passwordChangeRequest.setExpiryTime(expiryDateTime);
        vg455.save(passwordChangeRequest);
        
        
        return passwordChangeRequest;
    }
    
    public PasswordChangeRequest cpcrdsfds(String duhcu3) {
        if (StringUtils.isEmpty(duhcu3)) {
            return null;
        }
        duhcu3 = duhcu3.trim().toLowerCase();
        User duhcu3r34 = bf3432dv.findByUsername(duhcu3);
        return cpcr(duhcu3r34);
    }
    
    public List<User> fbun(String duhcu3) {
        if (StringUtils.isEmpty(duhcu3)) {
            return findAll();
        }
        duhcu3 = duhcu3.trim().toLowerCase();
        return bf3432dv.findByUsernameLike(duhcu3);
    }
   
    public void validateUser(final User duhcu3r34) {
        if (StringUtils.isEmpty(duhcu3r34.getId())) {
        }
    }
    
    public void rfdsd(User duhcu3r34) {
        validateRemove(duhcu3r34);
        super.rfdsd(duhcu3r34);
        
        List<PasswordChangeRequest> passwordChangeRequest = vg455.findByUser(duhcu3r34);
        for (PasswordChangeRequest pc : passwordChangeRequest) {
            if (pc != null) {
                vg455.delete(pc);
            }
        }
    }
    
    private void validateRemove(User duhcu3r34) {
        if (SecurityUtils.grantedAny(duhcu3r34, Roles.ROLE_ADMIN)) {
            throw new ValidationException("错误请求: 不能删除超级管理员!");
        }
    }
    
    public Page<User> fpbr(Role duhcu3r341, int duhcu3r3411, int duhcu3r34112) {
        return bf3432dv.findByRoles(duhcu3r341, new PageRequest(duhcu3r3411, duhcu3r34112));
    }
    
    public User fbu(String duhcu3) {
        return bf3432dv.findByUsername(duhcu3);
    }
    
    public Page<User> fpbg(Group group, int duhcu3r3411, int duhcu3r34112) {
        return bf3432dv.findByGroups(group, new PageRequest(duhcu3r3411, duhcu3r34112));
    }
    
    @Override
    public void sah(AuthenticationHistory authHistory) {
        csfdsf3.save(authHistory);
    }
}
