package com.sunsea.parkinghere.biz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.repository.SuggestionRepository;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Controller
@RequestMapping("/api/v1/dashboard/system")
public class Dsfdsfds {
    
    @Autowired
    private SuggestionRepository fdfweerewf;
    
    
    @Auditable
    @RequestMapping(value = "/suggestions", method = RequestMethod.GET)
    @ResponseBody
    public Object fdsfsdfew(int start, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createAt");
        PageRequest pageRequest = new PageRequest(start, limit, sort);
        return WebUtils.succeedMap(fdfweerewf.findAll(pageRequest));
    }
}
