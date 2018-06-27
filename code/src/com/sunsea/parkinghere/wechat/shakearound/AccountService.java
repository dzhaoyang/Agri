package com.sunsea.parkinghere.wechat.shakearound;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.popular.api.BaseAPI;

import com.sunsea.parkinghere.framework.web.WebUtils;

@Controller
@RequestMapping("/wechat/shakearound/account")
public class AccountService extends BaseAPI{
	

	
	@RequestMapping("/register")
	@ResponseBody
    public Object register(String name,String phone_number,String email,String industry_id){
		try{
			
			
			
			return WebUtils.succeedMap();
		}catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
}
