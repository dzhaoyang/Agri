package com.sunsea.parkinghere.biz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.SwitchType;
import com.sunsea.parkinghere.biz.service.Gdsfdsffrew3fv;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.BizServiceException;

@Controller
@RequestMapping(value = "/switch", method = RequestMethod.GET)
public class SC {

	@Autowired
	private SS vhyt3gbdfgset4;
	@Autowired
	private Gdsfdsffrew3fv fdfdfdsw;
	@Autowired
	private US ytresdfghtr;

	
	@RequestMapping(value = "/list")
	public String fsfdfdfsdfdfdfe333(ModelMap map){
		List<GreenHouse> greenHouses = fdfdfdsw.fbl();
		map.addAttribute("greenHouses", greenHouses);
		map.addAttribute("switchTypes", SwitchType.all());
		return "switch/list";
	}
	
	
	@RequestMapping(value = "/new")
	public String fdfdsfsd342ds(ModelMap map){
		Switch _switch = new Switch();
		map.put("data", _switch);
		List<GreenHouse> greenHouses = fdfdfdsw.fbl();
		map.addAttribute("greenHouses", greenHouses);
		map.addAttribute("switchTypes", SwitchType.all());
		return "switch/edit";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id,ModelMap map){
		Switch _switch = vhyt3gbdfgset4.fbi2(id);
		String typestr = "";
		
		if(_switch==null){
			_switch = new Switch();
		}else{
			typestr = SwitchType.getName(_switch.getType());
		}
		map.put("data", _switch);
		List<GreenHouse> greenHouses = fdfdfdsw.fbl();
		map.addAttribute("greenHouses", greenHouses);
		map.addAttribute("typestr", typestr);
		map.addAttribute("switchTypes", SwitchType.all());
		return "switch/edit";
	}
	
	
	@RequestMapping(value = "/contorlView/{id}")
	public String fdfdfdfdfddw2(@PathVariable String id,ModelMap map){
		try{
			Switch _switch = vhyt3gbdfgset4.fbi2(id);
			/*Page<Map<String, Object>> page = switchService.findSwitchOperateRecords(id, 0, 10);*/
			String typestr = "";
			String stauts = "";
			
			if(_switch==null){
				_switch = new Switch();
			}else{
				typestr = SwitchType.getName(_switch.getType());
			}
			stauts = SwitchType.getStatusName(_switch.getType(), _switch.getStauts());
			
			map.addAttribute("data", _switch);
			map.addAttribute("typestr", typestr);
			map.addAttribute("stauts", stauts);
			/*map.addAttribute("operateRecords", page.getContent());*/
			String operateName = SwitchType.getStatusName(_switch.getType(), _switch.getStauts()==1?0:1);
			map.addAttribute("operateName", operateName);
	        
			return "switch/contorlView";
		}catch(Exception e){
			e.printStackTrace();
			throw new BizServiceException(e.getMessage());
		}
	}
}
