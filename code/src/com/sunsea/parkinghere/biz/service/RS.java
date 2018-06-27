package com.sunsea.parkinghere.biz.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.model.Role;

public interface RS {
    
    public List<Role> fa();
    
    public Role fbi(String a);
    
    public Role fbn(String b);
    
    public Page<User> lmor(String a,
                                        String b,
                                        int v,
                                        int d);
    
    public void ga(String a, String dsfdsfd);
    
    public void rga(String a, String rolfdgfeId);
    
    public void sm();
}
