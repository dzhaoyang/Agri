package com.sunsea.parkinghere.biz.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.model.Group;

public interface GS extends IBizService<Group> {
    
    public boolean iugn(String a, String b);
    
    public boolean iugn(String a);
    
    public Group fbn(String a);
    
    public void var(Group a);
    
    public void varbi(String a);
    
    public void vap(Group a);
    
    public Page<User> lmog(String a,String b,int c,int d);
    
    public void am(String fds, String fdee3);
    
    public void da(String a, String b);
    
    public List<Group> fbnl(String a);
}
