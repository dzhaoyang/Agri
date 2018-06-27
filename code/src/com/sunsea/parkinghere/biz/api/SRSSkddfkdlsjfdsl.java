package com.sunsea.parkinghere.biz.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.SwitchType;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;


@Auditable
@Controller
@RequestMapping("/api/switch")
public class SRSSkddfkdlsjfdsl extends AbstractRestService {
	
	@Autowired
	private SS gfgrettre;
	@Autowired
	private US treregfddfgretr;
	
	@Auditable
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
	public Object poiuytewasdfghjknbvcxz(String uuid,String name,String greenHouseId,Integer switchType,Integer start,Integer limit){
		try{
			start = start==null?0:start;
			limit = limit==null?20:limit;
			Page<Switch> page = gfgrettre.fbp(uuid, name, greenHouseId, switchType, start, limit);
			for(Switch _switch : page.getContent()){
				_switch.setTypestr(SwitchType.getName(_switch.getType()));
			}
			return WebUtils.succeedMap(page);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@Auditable
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public Object fddfvadfgdfg(@PathVariable("id") String ffdsffsd){
		try{
			gfgrettre.fbi(ffdsffsd);
			return WebUtils.succeedMap();
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	
	@Auditable
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
	public Object iemdunuydbdj83939jednd(Switch iidjueneh3){
		try{
			if(StringUtils.isBlank(iidjueneh3.getId())){
				iidjueneh3.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
			gfgrettre.srewrewrew(iidjueneh3);
			Map<String, String> map = new HashMap<String, String>(1);
			map.put("id", iidjueneh3.getId());
			return WebUtils.succeedMap(map);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@Auditable
    @RequestMapping(value = "/close/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public Object fdsfsdfsdfadfsd(@PathVariable("id") String fgeewvfdv){
		try{
			User user = null;
			try{
				user = this.dsfsddfd3();
			}catch(Exception e){}
			if(user == null){
				treregfddfgretr.fbuds("admin");
			}
			gfgrettre.ut(fgeewvfdv, user, 0);
			return WebUtils.succeedMap(gfgrettre.fbi2(fgeewvfdv));
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@Auditable
    @RequestMapping(value = "/open/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public Object fdfsdfdsfdfsd(@PathVariable("id") String cvdvf){
		try{
			User user = null;
			try{
				user = this.dsfsddfd3();
			}catch(Exception e){}
			if(user == null){
				treregfddfgretr.fbuds("admin");
			}
			gfgrettre.ut(cvdvf, user, 1);
			return WebUtils.succeedMap(gfgrettre.fbi2(cvdvf));
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/findOperationList/{id}", method = RequestMethod.GET)
    @ResponseBody
	public Object fdsfsdfsdfsdfd(@PathVariable("id") String dfdsfsdfd, Integer start,Integer limit){
		try{
			start = start==null?0:start;
			limit = limit==null?10:limit;
			Page<Map<String, Object>> page = gfgrettre.fwor(dfdsfsdfd, start, limit);
			return WebUtils.succeedMap(page);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}

}
