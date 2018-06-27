package com.sunsea.parkinghere.openapi.app;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.openapi.NBizBaseFaceService;
import com.sunsea.parkinghere.openapi.NBizExceptionResult;
@Controller
@RequestMapping(value = "/api/app/switch")
public class SAASJJfdhsfsdf extends NBizBaseFaceService {
	
	@Autowired
	private SS fdsfsd432rfd;
	@Autowired
	private US gfgr43fd;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object fdsfsdfsdf3dc(@PathVariable String id){
		try{
			Switch _switch = fdsfsd432rfd.fbi2(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", _switch.getId());
			map.put("name", _switch.getName());
			map.put("status", _switch.getStauts());
			Long lastUpdateTime = null;
			if(StringUtils.isNotBlank(_switch.getLastUpdateTime())){
				lastUpdateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_switch.getLastUpdateTime()).getTime();
			}
			map.put("lastModifyTime", lastUpdateTime);
			map.put("lastModifyPerson", "");
			if(StringUtils.isNotBlank(_switch.getLastOperatorId())){
				User lastOperator = gfgr43fd.findById(_switch.getLastOperatorId());
				if(lastOperator!=null){
					map.put("lastModifyPerson", lastOperator.getName());
				}
			}
			map.put("type", _switch.getType());
			map.put("location", _switch.getLocation());
			return toSuccessResult(map);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/operate/{id}/{status}", method = RequestMethod.POST)
	@ResponseBody
	public Object fdff3rfdscsd(@PathVariable String id, @PathVariable Integer status){
		try{
			User user = null;
			try{
				user = this.getCurrentPrincipal();
			}catch(Exception e){
				return new NBizExceptionResult(1001001, "请登录！");
			}
			
			if(user==null){
				return new NBizExceptionResult(1001001, "请登录！");
			}
			fdsfsd432rfd.ut(id, user, status);
			return toSuccessResult(null);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
}
