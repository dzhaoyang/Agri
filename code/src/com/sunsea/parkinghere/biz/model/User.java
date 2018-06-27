package com.sunsea.parkinghere.biz.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sunsea.parkinghere.exception.ValidationException;
 
@Document(collection = "Users")
public class User extends Identity implements Serializable, UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    public static final String USERNAME_ADMINISTRATOR = "admin";
    
    @Indexed
    protected String username;
    
    private String name;
    
    private String displayName;
     
    private String email;
     
    @Indexed
    private String phoneNumber;
    
    private String title;
    
    @JsonIgnore
    private String password;
    
    @JsonIgnore
    private String passwordSalt;
    
    @JsonIgnore
    private String passwordEncoder = "MD5";
     
    private boolean expired = false;
     
    private boolean locked = false;
     
    private boolean enabled = true;
     
    private String expiryDate;
     
    private String department;  
     
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    
    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    //@JsonIgnore
    @DBRef
    private List<Role> roles;
    
    
    private String roleName;
    
    @JsonIgnore
    @DBRef
    private List<Group> groups;
    
    @JsonIgnore
    @Transient
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        if (StringUtils.isNotEmpty(username)) {
            username = username.trim().toLowerCase();
        }
        
        this.username = username;
    }
    
     
    public String getEmail() {
        return email;
    }
    
     
    public void setEmail(String email) {
        if (StringUtils.isNotEmpty(email)) {
            email = email.trim();
        }
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            name = name.trim();
        }
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPasswordSalt() {
        return passwordSalt;
    }
    
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    
    public String getPasswordEncoder() {
        return passwordEncoder;
    }
    
    public void setPasswordEncoder(String passwordEncoder) {
        if (StringUtils.isEmpty(passwordEncoder)) {
            this.passwordEncoder = "MD5";
        }
        if (!validatePasswordEncoder(passwordEncoder)) {
            throw new ValidationException(String.format("Unsupported password encoder '%s'",
                                                        passwordEncoder));
        }
        this.passwordEncoder = passwordEncoder.trim().toUpperCase();
    }
    
    private boolean validatePasswordEncoder(String passwordEncoder) {
        return ("MD5".equalsIgnoreCase(passwordEncoder.trim().toUpperCase()));
    }
    
    public boolean isExpired() {
        return expired;
    }
    
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
	public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	 
    public boolean isCredentialsNonExpired() {
        return !expired;
    }
    
     
    public boolean isAccountNonExpired() {
        return !expired;
    }
    
    public boolean isAccountNonLocked() {
        return !locked;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<Role> getRoles() {
    	if(roles == null){
    		roles = new ArrayList<Role>();
    	}
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public List<Group> getGroups() {
        return groups;
    }
    
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
     
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    public boolean isManager() {
        if (getRoles() == null) {
            return false;
        }
        for (Role role : getRoles()) {
            if (Roles.ROLE_MANAGER.equals(role.getName())) {
                return true;
            }
        }
        return false;
    }

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
