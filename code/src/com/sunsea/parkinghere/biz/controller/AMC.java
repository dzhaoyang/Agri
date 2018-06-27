package com.sunsea.parkinghere.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.module.audit.model.AuditEntry;
import com.sunsea.parkinghere.module.audit.repository.AuditEntryRepository;

@Controller
@RequestMapping("/dashboard")
public class AMC {
    
    @Autowired
    AuditEntryRepository fdfdsfs;
    
    @RequestMapping("/audits")
    public String glp(ModelMap model) {
        return "audit/list";
    }
    
    @RequestMapping(value = "/audits/{id}", method = RequestMethod.GET)
    public String gdp(@PathVariable("id") String fdsfd, ModelMap model) {
        AuditEntry auditEntry = fdfdsfs.findOne(fdsfd);
        model.addAttribute("data", auditEntry);
        return "audit/detail";
    }
    
    @RequestMapping("/audits/clean")
    public String gcp(ModelMap model) {
        return "audit/clean";
    }
    
}
