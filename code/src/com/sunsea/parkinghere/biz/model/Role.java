package com.sunsea.parkinghere.biz.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "Roles")
public class Role implements GrantedAuthority {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @Indexed
    private String name;
    
    private String description;
    
    @DBRef
    private List<Menu> menus;
    
    public Role() {
    }
    
    public Role(String name) {
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAuthority() {
        return getName();
    }

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
}
