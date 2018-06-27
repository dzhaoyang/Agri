package com.sunsea.parkinghere.biz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.service.Cftgdfhyh6hgf;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Auditable
@Controller
@RequestMapping("/api/customer")
public class CRS {

	@Autowired
	private Cftgdfhyh6hgf dsfd;
	
	@Auditable
    @RequestMapping(value = "/findCount", method = RequestMethod.POST)
    @ResponseBody
	public Object fc(Integer age1, Integer age2){
		try{
			return WebUtils.succeedMap(dsfd.c8(age1, age2));
		}catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
}
