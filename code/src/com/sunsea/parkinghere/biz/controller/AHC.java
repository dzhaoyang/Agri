package com.sunsea.parkinghere.biz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/authentications")
public class AHC {
    
    @RequestMapping("")
    public String glp(ModelMap model) {
        return "authentication/list";
    }

    @RequestMapping("/clean")
    public String gcp(ModelMap model) {
        return "authentication/clean";
    }

}
