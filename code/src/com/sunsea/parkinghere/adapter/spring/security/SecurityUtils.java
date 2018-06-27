package com.sunsea.parkinghere.adapter.spring.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sunsea.parkinghere.biz.model.User;

public class SecurityUtils {
    
    public static User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        
        if (principal instanceof User) {
            return (User) principal;
        }
        
        return null;
    }
    
    public static boolean grantedAny(String... roleNames) {
        return grantedAny(currentUser(), roleNames);
    }
    
    public static boolean grantedAny(User user, String... roleNames) {
        if (user == null) {
            return false;
        }
        
        Collection<? extends GrantedAuthority> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = user.getAuthorities();
        }
        
        return grantedAny(roles, roleNames);
    }
    
    private static boolean grantedAny(Collection<? extends GrantedAuthority> roles,
                                      String... roleNames) {
        if (roleNames == null || roleNames.length == 0) {
            return false;
        }
        
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        
        for (String roleName : roleNames) {
            if (granted(roles, roleName)) {
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean granted(Collection<? extends GrantedAuthority> roles,
                                   String roleName) {
        if (roleName == null || roles == null) {
            return false;
        }
        for (GrantedAuthority role : roles) {
            if (roleName.equals(role.getAuthority())) {
                return true;
            }
        }
        
        return false;
    }
}
