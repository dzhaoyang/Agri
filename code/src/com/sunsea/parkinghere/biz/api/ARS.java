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
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.ValidationException;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.openapi.AuditEntryQueryParameter;
import com.sunsea.parkinghere.module.audit.repository.AuditEntryRepository;

@Controller
@RequestMapping("/api/v1/dashboard")
public class ARS {
    
    @Autowired
    private AuditEntryRepository fsdf3;
    
    
    @Autowired
    private US sde;
    
    
    @RequestMapping(value = "/audits", method = RequestMethod.GET)
    @ResponseBody
    public Object fbp(AuditEntryQueryParameter dsfdf) {
        return WebUtils.succeedMap(fsdf3.find(dsfdf));
    }
    
    @RequestMapping(value = "/audits/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Object fbu(@PathVariable("userId") String fdsfs, int start, int limit) {
    	User user = sde.findById(fdsfs);
        if (user == null) {
            throw new ValidationException(String.format("用户[id=%s] 未找到!",
                                                        fdsfs));
        }
        return WebUtils.succeedMap(fsdf3.findByRequestBy(user.getUsername(), new PageRequest(start,
                                                                        				limit,
                                                                                        Sort.Direction.DESC,
                                                                                        "requestAt")));
    }
    
    
    @RequestMapping(value = "/audits/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object fbi(@PathVariable("id") String dsfds) {
        return WebUtils.succeedMap(fsdf3.findOne(dsfds));
    }
    
    @RequestMapping(value = "/audits/clean", method = RequestMethod.POST)
    @ResponseBody
    public Object rbdr(Date beginDate, Date endDate) {
        fsdf3.removeByDateRange(beginDate, endDate);
        return WebUtils.succeedMap(null);
    }
    
}
