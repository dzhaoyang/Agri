package com.sunsea.parkinghere.wechat.openapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechat/api/v1")
public class WeChatManagerService {
    
    
    
    @RequestMapping(value = "/text/send", method = RequestMethod.POST)
    @ResponseBody
    public Object sendTxtMsg(String msg) {
        return null;
    }
    
    
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public Object getMenus() {
        return null;
    }
    
   
    @RequestMapping(value = "/menus/update", method = RequestMethod.GET)
    @ResponseBody
    public Object updateMenus() {return null;}
    
    @RequestMapping(value = "/menus/updateBak", method = RequestMethod.GET)
    @ResponseBody
    public Object updateMenusBak() {return null;}
}
