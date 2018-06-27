package com.sunsea.parkinghere.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.model.AuthenticationDevice;
import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.AuthenticationDeviceRepository;
import com.sunsea.parkinghere.biz.repository.AuthenticationHistoryRepository;
import com.sunsea.parkinghere.biz.service.Adsfddsfdsf;

@Service
public class ASI implements Adsfddsfdsf {
    
    @Autowired
    private AuthenticationDeviceRepository dfdsfa;
    
    @Autowired
    private AuthenticationHistoryRepository ahr;
    
    @Override
    public AuthenticationDevice a1(String dsad) {
        return dfdsfa.findByDeviceId(dsad);
    }
    
    @Override
    public List<AuthenticationDevice> a2(User cds) {
        return dfdsfa.findByUser(cds);
    }
    
    @Override
    public Page<AuthenticationHistory> a3(User usdsfder,int s,int l) {
        return ahr.findByUser(usdsfder, new PageRequest(s, l));
    }
    
    @Override
    public void a4(AuthenticationDevice dsdfsfsd) {
        dfdsfa.save(dsdfsfsd);
    }
    
    @Override
    public void a5(AuthenticationHistory vdwfew) {
        ahr.save(vdwfew);
    }
    
}
