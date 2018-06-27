package com.sunsea.parkinghere.biz.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Roles {
    
    public static final String ROLE_USER = "ROLE_USER";
    
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    
    public static final String ROLE_PARKMANAGER = "ROLE_PARKMANAGER";
    
    public static final String ROLE_PARKSTAFF = "ROLE_PARKSTAFF";
    
    public static final String ROLE_CAROWNER = "ROLE_CAROWNER";
    
    public static final String ROLE_SALESMAN = "ROLE_SALESMAN";
    
    public static String[] ROLES = new String[] { ROLE_USER,
                                                 ROLE_MANAGER,
                                                 ROLE_ADMIN,
                                                 ROLE_PARKSTAFF,
                                                 ROLE_PARKMANAGER,
                                                 ROLE_CAROWNER,
                                                 ROLE_SALESMAN };
    
    private static Map<String, String> friendlyRoleNameMap = new HashMap<String, String>();
    
    static {
        friendlyRoleNameMap.put(ROLE_USER, "用户");
        friendlyRoleNameMap.put(ROLE_MANAGER, "系统管理员");
        friendlyRoleNameMap.put(ROLE_ADMIN, "超级管理员");
    }
    
    public static class RolesHolder {
        
        private static final Roles instance = new Roles();
        
    }
    
    public static final Roles getInstance() {
        return RolesHolder.instance;
    }
    
    public String getFriendlyRoleName(String roleName) {
        String friendlyRoleName = friendlyRoleNameMap.get(roleName);
        return StringUtils.isEmpty(friendlyRoleName) ? roleName
                                                    : friendlyRoleName;
    }
}
