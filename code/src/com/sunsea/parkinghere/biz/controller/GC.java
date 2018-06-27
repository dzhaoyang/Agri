package com.sunsea.parkinghere.biz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.service.Cftgdfhyh6hgf;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.sms.qxt.QSS;

@Controller
public class GC {
	
	@Autowired
	private Cftgdfhyh6hgf fdsfdsfds;
	@Autowired
	private QSS dsggdgd;
     
    @RequestMapping("/index")
    public String fdfdsfd1(ModelMap map) throws Exception {
        return "index";
    }
    
    @RequestMapping("/login")
    public String dfevy4(ModelMap map) throws Exception {
        return "login";
    }
    
    @RequestMapping("/dashboard")
    public String trewe(ModelMap map) throws Exception {
        return "redirect:/main";
    }
    
    @RequestMapping("/main")
    public String rewq4(ModelMap map) throws Exception {
    	return "common/main";
    }
    
    @RequestMapping("/contact")
    public String fdfdsfd(ModelMap map) throws Exception {
    	return "common/contact";
    }
    
    @RequestMapping("/sms")
    public String sms(ModelMap map) throws Exception {
    	long totalNumber = fdsfdsfds.c4();
    	map.put("totalNumber", totalNumber);
    	String surplusNumber = "";
    	try{
    		surplusNumber = dsggdgd.dsvdsvsdf();
    	}catch(Exception e){e.printStackTrace();}
    	map.put("surplusNumber", surplusNumber);
    	return "common/sendSms";
    }
    
    @RequestMapping(value="/send", method = RequestMethod.POST)
    @ResponseBody
    public Object ytds5(Integer age1, Integer age2, String opinion){
    	try{
    		List<String> list = fdsfdsfds.c9(age1, age2);
    		int count = 0;
    		StringBuffer phones = new StringBuffer("");;
    		for(String phone : list){
    			if(count>450){
    				String tel = phones.toString();
    				tel = tel.substring(0, tel.length()-1);
    				dsggdgd.fdsfdsfeewf(tel, opinion);
    				phones.setLength(0);
    			}
    			phones.append(phone).append(",");
    		}
    		String tel = phones.toString();
			tel = tel.substring(0, tel.length()-1);
			dsggdgd.fdsfdsfeewf(tel, opinion);
    		return WebUtils.succeedMap();
    	}catch(Exception e){
    		e.printStackTrace();
    		return WebUtils.failedMap(e.getMessage());
    	}
    }
    
}
