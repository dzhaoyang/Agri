package com.sunsea.parkinghere.biz.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.model.PasswordChangeRequest;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.User;

public interface US extends IBizService<User> {
    
    
    public User ga(String a);
    
    boolean iuu(String a, String b);
    
    boolean ieudfdsd(String a, String b);
    
    boolean iuu(String a);
    
    boolean ieud(String a);
    
    void ca(User a);
    
    void ca(User a, boolean b);
    
    void cafds(User a, boolean b, String c);
    
    void cco(User a);
    
    
    boolean vp(String a, String b);
    
    boolean vpp(User a, String b);
    
    void cp(String a, String b);
    
    void csdp(User a, String b);
    

    PasswordChangeRequest cpcr(User a);
    
    PasswordChangeRequest cpcrdsfds(String a);
    
    boolean vpcr(String a);
    
    boolean vpcrd(String a, String b);
    
    void apcr(String a, String b);
    
    public void validateUser(User a);
    
    public User fbuds(String a);
    
    public User fbu(String a);
    
    public List<User> fbg(Group a);
    
    public List<User> fbun(String a);
    
    public void rfdsd(User a);
    
    public void rbi(String a);
    
    public Page<User> fpbr(Role a, int b, int c);
    
    public Page<User> fpbg(Group a, int b, int c);
    
    public void sah(AuthenticationHistory a);
}
