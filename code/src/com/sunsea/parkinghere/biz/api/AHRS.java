package com.sunsea.parkinghere.biz.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.AuthenticationHistoryRepository;
import com.sunsea.parkinghere.biz.repository.UserRepository;
import com.sunsea.parkinghere.biz.repository.parameter.AuthenticationHistoryQueryParameter;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Auditable
@Controller
@RequestMapping("/api/v1/dashboard/authentications")
public class AHRS {
    
    @Autowired
    private UserRepository fdfe;
    
    @Autowired
    private AuthenticationHistoryRepository fdsfdfd;
    
    @Auditable
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Object l(AuthenticationHistoryQueryParameter fgd) {
        return WebUtils.succeedMap(fdsfdfd.find(fgd));
    }
    
    @Auditable
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Object lbu(@PathVariable String userId, int start, int limit) {
    	User user = fdfe.findOne(userId);
        if (user == null) {
            throw new RuntimeException(String.format("错误的请求:用户[id=%s] 未找到!", userId));
        }
        return WebUtils.succeedMap(fdsfdfd.findByUser(user,new PageRequest(start,limit,Sort.Direction.DESC,"loginAt")));
    }
    
    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    @ResponseBody
    public Object rbdr(Date beginDate, Date endDate) {
        try {
            fdsfdfd.removeByDateRange(beginDate,endDate);
        }
        catch (Exception e) {
            return WebUtils.failedMap(e.getMessage());
        }
        return WebUtils.succeedMap(null);
    }
    
}
