package com.sunsea.parkinghere.biz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.sunsea.parkinghere.Configuration;
import com.sunsea.parkinghere.biz.cache.MemCachedManager;
import com.sunsea.parkinghere.biz.cache.MemCachedRoles;
import com.sunsea.parkinghere.biz.model.ApplicationSetting;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.Roles;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.ApplicationSettingRepository;
import com.sunsea.parkinghere.biz.repository.RoleRepository;
import com.sunsea.parkinghere.biz.repository.UserRepository;
import com.sunsea.parkinghere.framework.utils.PasswordUtils;

@Component
public class ApplicationStartupCallback implements
                                       ApplicationListener<ContextRefreshedEvent>,
                                       InitializingBean {
    
    private static final Log logger = LogFactory.getLog(ApplicationStartupCallback.class);
    
    @Autowired
    private ApplicationSettingRepository applicationSettingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("On ContextStartedEvent");
        
        initializeApplication();
        loadAllRolesToMem();
    }
    
    private void loadAllRolesToMem() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        MemCachedManager.getInstance().put(Role.class,
                                           new MemCachedRoles(roles));
    }
    
    private void initializeApplication() {
        ApplicationSetting applicationSetting = applicationSettingRepository.findByKey("initialized");
        if (applicationSetting != null && "true".equalsIgnoreCase(applicationSetting.getValue())) {
            logger.debug("The application has been initialized.");
            return;
        }
        
        logger.debug("Start to initialize the application, including the administrator user and the default roles.");
        for (String roleName : Roles.ROLES) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                logger.debug(String.format("%s not found, will create.",
                                           roleName));
                role = new Role();
                role.setName(roleName);
                role.setDescription(Roles.getInstance()
                                         .getFriendlyRoleName(roleName));
                roleRepository.save(role);
            }
        }
        
        User user = userRepository.findByUsername(User.USERNAME_ADMINISTRATOR);
        if (user == null) {
            logger.debug(String.format("User %s not found, will create.", User.USERNAME_ADMINISTRATOR));
            user = new User();
            user.setUsername(User.USERNAME_ADMINISTRATOR);
            user.setEmail(Configuration.getInstance().getAdministratorEmail());
            user.setName("超级管理员");
            user.setDisplayName("超级管理员");
            user.setPasswordSalt(PasswordUtils.generatePasswordSalt());
            String passwordHash = PasswordUtils.generatedSaltedPasswordHash(User.USERNAME_ADMINISTRATOR,
                                                                            user.getPasswordSalt(),
                                                                            user.getPasswordEncoder());
            user.setPassword(passwordHash);
            user.setRoles((List<Role>) roleRepository.findAll());
            userRepository.save(user);
        }
        
        if (applicationSetting == null) {
            applicationSetting = new ApplicationSetting();
            applicationSetting.setKey("initialized");
        }
        applicationSetting.setValue("true");
        applicationSettingRepository.save(applicationSetting);
        logger.debug("The application has been initialized.");
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    }
    
}
