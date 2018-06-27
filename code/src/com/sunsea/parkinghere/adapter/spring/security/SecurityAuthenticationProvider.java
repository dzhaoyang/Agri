package com.sunsea.parkinghere.adapter.spring.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

@SuppressWarnings("deprecation")
public class SecurityAuthenticationProvider extends
                                           AbstractUserDetailsAuthenticationProvider {
    
    private PasswordEncoder passwordMd5Encoder = new Md5PasswordEncoder();
    
    /*private PasswordEncoder passwordSha1Encoder = new ShaPasswordEncoder();*/
    
    private SaltSource saltSource;
    
    private UserDetailsService userDetailsService;
    
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService,
                       "A UserDetailsService must be set");
    }
    
    /**
     * Implementation of an abstract method defined in the base class. The
     * retrieveUser() method is called by authenticate() method of the base
     * class. The latter is called by the AuthenticationManager.
     */
    protected final UserDetails retrieveUser(String username,
                                             UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails details;
        try {
            details = this.getUserDetailsService().loadUserByUsername(username);
            authentication.setDetails(details);
        }
        catch (DataAccessException repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(),
                                                     repositoryProblem);
        }
        
        if (details == null) {
            throw new AuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        }
        return details;
    }
    
    /**
     * Implementation of an abstract method defined in the base class. The
     * additionalAuthenticationChecks() method is called by authenticate()
     * method of the base class after the invocation of retrieveUser() method.
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;
        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }
        
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                                  "Bad credentials"),
                                              null);
        }
        
        String presentedPassword = authentication.getCredentials().toString();
        
        PasswordEncoder _passwordEncoder = passwordMd5Encoder;
        // if (userDetails instanceof User) {
        // User account = (User) userDetails;
        // String passwordEncoderType = account.getPasswordEncoder();
        // if (User.MD5_PASSWORD_ENCODER.equalsIgnoreCase(passwordEncoderType))
        // {
        // _passwordEncoder = passwordSha1Encoder;
        // }
        // }
        
        if (!_passwordEncoder.isPasswordValid(userDetails.getPassword(),
                                              presentedPassword,
                                              salt)) {
            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                                  "Bad credentials"),
                                              null);
        }
    }
    
    /**
     * The source of salts to use when decoding passwords.
     * 
     * @param saltSource
     */
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }
    
    protected SaltSource getSaltSource() {
        return saltSource;
    }
    
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
