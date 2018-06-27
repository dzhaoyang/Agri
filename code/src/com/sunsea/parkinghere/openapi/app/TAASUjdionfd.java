package com.sunsea.parkinghere.openapi.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sunsea.parkinghere.biz.dto.TfjdisfjsdiDto;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataType;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.openapi.NBizBaseFaceService;
import com.sunsea.parkinghere.openapi.NBizExceptionResult;
import com.sunsea.parkinghere.openapi.NBizSuccessResult;

@Controller
@RequestMapping(value = "/api/app/transducer")
public class TAASUjdionfd extends NBizBaseFaceService {
	
	@Autowired
	private TS dffdsfdsfdsffds3;

	
	@RequestMapping(value = "/today/info/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object gfdgfgdfdfgfdv(@PathVariable String id){
		try{
			Transducer t = dffdsfdsfdsffds3.fbi(id);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("id", t.getId());
			map.put("name", t.getName());
			map.put("uuid", t.getUuid());
			map.put("type", t.getType());
			map.put("joinDate", null);
			map.put("expireDate", null);
			map.put("location", t.getLocation());
			
			List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
			map.put("values", values);
			if(t.getTransducerDataTypes()!=null){
				for(TransducerDataType tt : t.getTransducerDataTypes()){
					Map<String, Object> ttMap = new LinkedHashMap<String, Object>();
					values.add(ttMap);
					
					ttMap.put("dataType", tt.getDataType());
					ttMap.put("measure", tt.getMeasure());
					ttMap.put("upperLimit", tt.getUpperLimit());
					ttMap.put("lowerLimit", tt.getLowerLimit());
					ttMap.put("currentValue", tt.getValue());
					ttMap.put("lastModifyTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tt.getLastUpdateTime()).getTime());
				}
			}
			
			return toSuccessResult(map);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/data/{id}/{date}", method = RequestMethod.GET)
	@ResponseBody
	public Object fdsfdsf44fcdsc(@PathVariable("id") String fdfds, @PathVariable("date") Long fdsfdfd){
		try{
			Date date_ = new Date(fdsfdfd);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Transducer t = dffdsfdsfdsffds3.fbi(fdfds);
			List<TfjdisfjsdiDto> list = dffdsfdsfdsffds3.f24hrbd(fdfds, sdf.format(date_));
			
			List<Map<String, Object>> returnList = new ArrayList<>();
			
			if(t.getTransducerDataTypes()==null){
				return toSuccessResult(returnList);
			}
			for(TransducerDataType tt : t.getTransducerDataTypes()){
				Map<String, Object> returnMap = new LinkedHashMap<>();
				Integer dataType = tt.getDataType();
				List<Map<String, Object>> values = new ArrayList<>();
				for(TfjdisfjsdiDto t24 : list){
					if(dataType.intValue() == t24.getDataType().intValue()){
						Map<String, Object> valueMap = new LinkedHashMap<>();
						valueMap.put("value", t24.getValue());
						valueMap.put("measure", tt.getMeasure());
						valueMap.put("time", t24.getHour());
						values.add(valueMap);
					}
				}
				returnMap.put("dataType", dataType);
				returnMap.put("values", values);
				returnList.add(returnMap);
			}
			return toSuccessResult(returnList);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/data/{id}/{startDate}/{endDate}", method = RequestMethod.GET)
	@ResponseBody
	public Object fdsfsdfsdfdsfsdfsd(@PathVariable String id, @PathVariable Long startDate, @PathVariable Long endDate){
		try{
			Date startTime = new Date(startDate);
			Date endTime = new Date(endDate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Transducer t = dffdsfdsfdsffds3.fbi(id);
			List<TfjdisfjsdiDto> list = dffdsfdsfdsffds3.fdrbat(id, sdf.format(startTime), sdf.format(endTime));
			
			List<Map<String, Object>> returnList = new ArrayList<>();
			
			if(t.getTransducerDataTypes()==null){
				return toSuccessResult(returnList);
			}
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(TransducerDataType tt : t.getTransducerDataTypes()){
				Map<String, Object> returnMap = new LinkedHashMap<>();
				Integer dataType = tt.getDataType();
				List<Map<String, Object>> values = new ArrayList<>();
				for(TfjdisfjsdiDto t24 : list){
					if(dataType.intValue() == t24.getDataType().intValue()){
						Map<String, Object> valueMap = new LinkedHashMap<>();
						valueMap.put("day", sdf.parse(t24.getDay()).getTime());
						valueMap.put("vagValue", t24.getValue());
						valueMap.put("maxValue", t24.getMaxValue());
						valueMap.put("minValue", t24.getMinValue());
						valueMap.put("measure", tt.getMeasure());
						values.add(valueMap);
					}
				}
				
				returnMap.put("dataType", dataType);
				returnMap.put("values", values);
				returnList.add(returnMap);
			}
			NBizSuccessResult r = toSuccessResult(returnList);
			
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
}
