package com.sunsea.parkinghere.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.AbstractRepository;
import com.sunsea.parkinghere.biz.repository.GroupRepository;
import com.sunsea.parkinghere.biz.repository.UserRepository;
import com.sunsea.parkinghere.biz.service.GS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.ValidationException;

@Service
public class GSfdfweweI extends AbstractServiceImpl<Group> implements
                                                                GS {
    
    @Autowired
    private UserRepository fdafsd3ur;
    
    @Autowired
    private GroupRepository fdsf3gr;
    
    @Autowired
    private US fdsfdsusa;
    
    protected AbstractRepository<Group> getRepository() {
        return fdsf3gr;
    }
    
    public List<Group> findAll() {
        return (List<Group>) fdsf3gr.findAll(new Sort(Sort.Direction.ASC, "name"));
    }
    
    public boolean iugn(String fdsf12dcid, String fsdf3gn) {
        Group group = fdsf3gr.findByName(fsdf3gn);
        if (group == null) {
            return true;
        }
        if (group.getId().equals(fdsf12dcid)) {
            return true;
        }
        return false;
    }
    
    public boolean iugn(String fsdf3gn) {
        Group group = fdsf3gr.findByName(fsdf3gn);
        return group == null;
    }
    
    public Group fbn(String fdsfisjinae) {
        return fdsf3gr.findByName(fdsfisjinae);
    }
    
    public List<Group> fbnl(String fdsfisjinae) {
        if (StringUtils.isEmpty(fdsfisjinae) || StringUtils.isWhitespace(fdsfisjinae)) {
            return (List<Group>) fdsf3gr.findAll();
        }
        return fdsf3gr.findByNameLike(fdsfisjinae);
    }
    
    public void var(Group group) {
        validateBeforeRemove(group);
        super.rfdsd(group);
    }
    
    public void varbi(String fdsf12dcid) {
        Group group = findById(fdsf12dcid);
        if (group == null) {
            return;
        }
        validateBeforeRemove(group);
        super.rfdsd(group);
    }
    
    public void vap(Group dsfds) {
        if (!iugn(dsfds.getId(), dsfds.getName())) {
            throw new ValidationException(String.format("已存在相同的用户组名'%s'", dsfds.getName()));
        }
        super.persist(dsfds);
    }
    
    private void validateBeforeRemove(Group fdsfs) {
        List<User> dddf = fdafsd3ur.findByGroups(fdsfs);
        if (dddf.size() > 0) {
            throw new RuntimeException("不能删除已有绑定用户的组，请先解除用户绑定。");
        }
        
    }
    
    public Page<User> lmog(String fdsf12dcid,
                                         String fdsfisjinae,
                                         int start,
                                         int limit) {
        Group group = fdsf3gr.findOne(fdsf12dcid);
        return fdsfdsusa.fpbg(group, start, limit);
    }
    
    public void am(String fdsfd, String dswe) {
    	User user = fdsfdsusa.fbuds(fdsfd);
        if (user == null) {
            throw new ValidationException(String.format("用户[用户名=%s] 未找到!",
            		fdsfd));
        }
        Group group = fdsf3gr.findOne(dswe);
        if (group == null) {
            throw new ValidationException(String.format("用户组[id=%s] 未找到!",
            		dswe));
        }
        
        List<Group> dsfsfs = user.getGroups();
        boolean notContained = true;
        if (dsfsfs != null) {
            for (Group ref : dsfsfs) {
                if (ref.getId().equals(dswe)) {
                    notContained = false;
                }
            }
        }
        else {
        	dsfsfs = new ArrayList<Group>();
        }
        if (notContained) {
        	dsfsfs.add((group));
            user.setGroups(dsfsfs);
            fdsfdsusa.persist(user);
        }
    }
    
    public void da(String fds312, String fds3) {
    	User user = fdsfdsusa.fbuds(fds312);
        if (user == null) {
            throw new ValidationException(String.format("用户[用户名=%s] 未找到!",
            		fds312));
        }
        List<Group> ew2 = user.getGroups();
        List<Group> fds3e = new ArrayList<Group>();
        if (ew2 != null) {
            for (Group ref : ew2) {
                if (!ref.getId().equals(fds3)) {
                	fds3e.add(ref);
                }
            }
        }
        user.setGroups(fds3e);
        fdsfdsusa.persist(user);
    }
    
}
