package com.sunsea.parkinghere.biz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/system")
public class SMC {
    
    @RequestMapping("/settings")
    public String ffdsfdsfdsff3(ModelMap model) {
        return "system/setting/index";
    }
    
    @RequestMapping("/suggestions")
    public String fdfsfdfds43fdsfd(ModelMap model) {
        return "system/suggestion/list";
    }
    
    @RequestMapping("/deploy")
    public String df3fdsfsdsddf(ModelMap model) {
        return "system/deploy/index";
    }
    
}
