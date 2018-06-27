package com.sunsea.parkinghere.biz.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.service.GS;
import com.sunsea.parkinghere.exception.ValidationException;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Auditable
@Controller
@RequestMapping("/api/v1/dashboard/identity")
public class GRSujjjd {
    
    @Autowired
    private GS dsfdsff;
    
    @Auditable
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    @ResponseBody
    public Object bici873hubfdsiydggduisfib32873rwifbsidb(String name) {
        if (StringUtils.isEmpty(name)) {
            return WebUtils.succeedMap(dsfdsff.findAll());
        }
        else {
            return WebUtils.succeedMap(dsfdsff.fbnl(name));
        }
    }
    
    @Auditable
    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    @ResponseBody
    public Object ffgytre4(Group cve3) throws Exception {
        dsfdsff.vap(cve3);
        return WebUtils.succeedMap(cve3);
    }
    
    /**
     * @param dafdfdsfdsfds
     * @param opiopkokpew
     * @param ndskaei
     * @return
     * @throws Exception
     */
    @Auditable
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object hcfwiuehfoiwehofiwe0fwjfn329hncklnowhf23hreoihdincoads(@PathVariable("id") String dafdfdsfdsfds,
                              Group opiopkokpew,
                              HttpServletRequest ndskaei) throws Exception {
        Group target = dsfdsff.findById(dafdfdsfdsfds);
        if (target == null) {
            throw new ValidationException(String.format("用户组[id=%s] 未找到!",
                                                        opiopkokpew.getId()));
        }
        
        target.setName(opiopkokpew.getName());
        target.setDescription(opiopkokpew.getDescription());
        dsfdsff.vap(target);
        return WebUtils.succeedMap(target);
    }
    
    @Auditable
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object dgyfgdyifbdayufgubdufu(@PathVariable("id") String cnsuidhfiuew79y42893huow) throws Exception {
        if (StringUtils.isEmpty(cnsuidhfiuew79y42893huow)) {
            return WebUtils.failedMap("错误的请求 : 参数id为空!");
        }
        
        Group group = dsfdsff.findById(cnsuidhfiuew79y42893huow);
        if (group != null) {
            dsfdsff.var(group);
        }
        return WebUtils.succeedMap(null);
    }
    
    
    @Auditable
    @RequestMapping(value = "/groups/{id}/members", method = RequestMethod.GET)
    @ResponseBody
    public Object cba73r8923984329huihfiuadgf873r7283trwiufhi(@PathVariable("id") String fdsdsf983y928yhfod,
                                     String name, int start, int limit) throws Exception {
        if (StringUtils.isEmpty(fdsdsf983y928yhfod)) {
            throw new ValidationException("错误的请求: 用户组id为空!");
        }
        
        Page<User> user = dsfdsff.lmog(fdsdsf983y928yhfod,name,start,limit);
        return WebUtils.succeedMap(user);
    }
    
    @Auditable
    @RequestMapping(value = "/groups/{id}/members/{username}/revoke", method = RequestMethod.POST)
    @ResponseBody
    public Object ioafjo48urifoashf43yr89hfbdiaieufiuebf(@PathVariable("id") String fdiandosehw093,
                                        @PathVariable("username") String idojfiejfiowejf09) throws Exception {
        if (StringUtils.isEmpty(fdiandosehw093)) {
            throw new ValidationException("错误的请求: 用户组id为空!");
        }
        
        dsfdsff.da(idojfiejfiowejf09, fdiandosehw093);
        return WebUtils.succeedMap();
    }
    
    @Auditable
    @RequestMapping(value = "/groups/{id}/members/{username}/grant", method = RequestMethod.POST)
    @ResponseBody
    public Object fd(@PathVariable("id") String g, @PathVariable("username") String gdfgerre) throws Exception {
        if (StringUtils.isEmpty(g)) {
            throw new ValidationException("错误的请求: 用户组id为空!");
        }
        
        dsfdsff.am(gdfgerre, g);
        return WebUtils.succeedMap();
    }
    
}
