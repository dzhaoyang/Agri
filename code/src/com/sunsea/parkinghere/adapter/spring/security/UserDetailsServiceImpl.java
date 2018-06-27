package com.sunsea.parkinghere.adapter.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.US;

public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private US userService;
    
    /**
     * @param username
     *            the account name in email format
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,
                                                          DataAccessException {
    	User user = userService.ga(username);
        if (user == null) {
        	throw new UsernameNotFoundException(String.format("Unable to find username %s", username));
        }
        return user;
    }
}
