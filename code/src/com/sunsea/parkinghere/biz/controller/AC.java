package com.sunsea.parkinghere.biz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/analysis")
public class AC {

	@RequestMapping("/view")
	public String view(ModelMap map){
		return "/analysis/analysis";
	}
}
