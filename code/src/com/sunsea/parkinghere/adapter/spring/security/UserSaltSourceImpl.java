package com.sunsea.parkinghere.adapter.spring.security;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

import com.sunsea.parkinghere.biz.model.User;

public class UserSaltSourceImpl implements SaltSource {
    
    public Object getSalt(UserDetails details) {
    	User user = (User) details;
        return user.getPasswordSalt();
    }
    
}
