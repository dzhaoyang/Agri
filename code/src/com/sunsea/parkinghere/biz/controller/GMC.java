package com.sunsea.parkinghere.biz.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.service.GS;
import com.sunsea.parkinghere.exception.ValidationException;

@Controller
@RequestMapping(value = "/dashboard/identity", method = RequestMethod.GET)
public class GMC {
    
    @Autowired
    private GS fdfdfdsfs;
    
    @RequestMapping("/groups")
    public String vdsfdfdsfdsf(ModelMap map) {
        return "group/list";
    }
    
    @RequestMapping("/groups/new")
    public String gfdgdgg(ModelMap map) throws Exception {
        return "group/edit";
    }
    
    @RequestMapping("/groups/{id}/edit")
    public String hghghtrter(@PathVariable String id, ModelMap map) throws Exception {
        Group group = fdfdfdsfs.findById(id);
        if (group == null) {
            throw new ValidationException(String.format("Group[id=%s] not found!",
                                                        id));
        }
        map.put("group", group);
        return "group/edit";
    }
    
    // ********************members*********************
    @RequestMapping("/groups/{id}/members")
    public String hgfdqertyui(@PathVariable String id,
                                            ModelMap map,
                                            HttpSession session) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new ValidationException("Bad Request: the group id is null!");
        }
        
        Group group = fdfdfdsfs.findById(id);
        if (group != null) {
            map.put("group", group);
            return "group/members";
        }
        
        throw new ValidationException("Bad Request : invalid group id!");
    }
    
}
