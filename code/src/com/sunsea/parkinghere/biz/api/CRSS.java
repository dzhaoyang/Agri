package com.sunsea.parkinghere.biz.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.Configuration;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;
/**
 * 配置文件重新加载
 * @author ylr
 *
 */
@Auditable
@Controller
@RequestMapping("/api/configuration")
public class CRSS {

	@Auditable
    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    @ResponseBody
    public Object r(){
		try{
			Configuration configuration = Configuration.getInstance();
			configuration.reload();
			return WebUtils.succeedMap();
		}catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
}
