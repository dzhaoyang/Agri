package com.sunsea.parkinghere.module.carowner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.module.carowner.model.CarOwnerNonceContext;
import com.sunsea.parkinghere.module.carowner.service.CarOwnerNonceService;

@Controller
@RequestMapping("/_debuger_/app/carowner")
public class CarOwnerDebugerContoller extends AbstractController {
    
    public static final String testUsername = "testwechatcarowneruser";
    
    @Autowired
    private CarOwnerNonceService carOwnerNonceService;

    
    @RequestMapping("")
    public String index(ModelMap model) {
        
        CarOwnerNonceContext context = carOwnerNonceService.generateContext(testUsername,
                                                                            -1,-1,
                                                                            "测试地址");

        model.addAttribute("context", context);
        
        return "app/carowner/_debuger_";
    }
    
    @RequestMapping("/parkings/reindex")
    @ResponseBody
    public String reIndexParkings() {
        return "Re index parkings data done!";
    }
    
}
