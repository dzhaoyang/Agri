package com.sunsea.parkinghere.wechat.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechat/oauth2")
public class WeChatOAuth2Callback {
    static final Log logger = LogFactory.getLog(WeChatOAuth2Callback.class);
   
    
    
    @RequestMapping("/authorize/{scope}/{id}")
    public String authorize(@PathVariable String scope,@PathVariable String id) throws UnsupportedEncodingException{
    	System.out.println("");
    	String redirect_uri = URLEncoder.encode("/wechat/oauth2/callback","utf-8");
    	
    	return "";
    }
    
   
    @RequestMapping("/callback")
    public String callback(String code, String state, ModelMap map) throws UnsupportedEncodingException {
        try{
        	
        }catch(Exception e){
        	e.printStackTrace();
        	map.put("isCan", false);
    		map.put("msg", "请重新尝试！");
        	return "weixin/error";
        }
        return "";
    }
    
}
