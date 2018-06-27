package com.sunsea.parkinghere.openapi.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataType;
import com.sunsea.parkinghere.biz.service.Gdsfdsffrew3fv;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.framework.utils.ArithmeticUtil;
import com.sunsea.parkinghere.openapi.NBizBaseFaceService;
import com.sunsea.parkinghere.openapi.NBizExceptionResult;


@Controller
@RequestMapping("/api/app/greenHouse")
public class GHAAS extends NBizBaseFaceService {
	
	@Autowired
	private Gdsfdsffrew3fv fdsadfdsfd3;
	@Autowired
	private TS fdfdfgr5gfg34;
	@Autowired
	private SS ffdfdre3fds3;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object fdsfdsfdsfdsfd(){
		try{
			List<GreenHouse> greenHouses = fdsadfdsfd3.fbl();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(GreenHouse gh : greenHouses){
				if(StringUtils.isNotBlank(gh.getMapPhotoFileName())){
					Map<String, Object> map = new HashMap<>(2);
					map.put("id", gh.getId());
					map.put("name", gh.getName());
					list.add(map);
				}
			}
			return toSuccessResult(list);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/map/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object fdjsiofnsdfo3(@PathVariable("id") String iijiubbdsuay){
		try{
			GreenHouse gh = fdsadfdsfd3.fbi(iijiubbdsuay);
			List<Transducer> transducers = fdfdfgr5gfg34.fbl(iijiubbdsuay);
			List<Switch> switchs = ffdfdre3fds3.fbl(iijiubbdsuay);
			
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("id", gh.getId());
			returnMap.put("name", gh.getName());
			returnMap.put("mapUrl", "/media/photo/"+gh.getMapPhotoFileName());
			returnMap.put("mapWidth", gh.getMapWidth());
			returnMap.put("mapHeight", gh.getMapHeight());
			returnMap.put("version", gh.getVarsion());
			
			List<Map<String, Object>> transducerList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> switchList = new ArrayList<Map<String, Object>>();
			returnMap.put("switchList", switchList);
			returnMap.put("transducerList", transducerList);
			
			for(Switch _switch : switchs){
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				switchList.add(map);
				
				map.put("id", _switch.getId());
				map.put("name", _switch.getName());
				map.put("type", _switch.getType());
				map.put("coordinateX", _switch.getCoordinateX());
				map.put("coordinateY", _switch.getCoordinateY());
				map.put("status", _switch.getStauts());
			}
			
			for(Transducer t : transducers){
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				transducerList.add(map);
				
				map.put("id", t.getId());
				map.put("name", t.getName());
				map.put("type", t.getType());
				map.put("coordinateX", t.getCoordinateX());
				map.put("coordinateY", t.getCoordinateY());
				
				List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
				map.put("statusList", statusList);
				if(t.getTransducerDataTypes()==null){
					continue;
				}
				for(TransducerDataType tt : t.getTransducerDataTypes()){
					Map<String, Object> ttMap = new LinkedHashMap<String, Object>();
					statusList.add(ttMap);
					ttMap.put("dataType", tt.getDataType());
					Integer status = null;
					if(tt.getValue()==null){
						status = 0;
					}else if(tt.getValue()>tt.getUpperLimit()){
						status = 1;
					}else if(tt.getValue()<tt.getLowerLimit()){
						status = -1;
					}else{
						status = 0;
					}
					//System.out.println("status = "+status+"; Value = "+tt.getValue()+"; UpperLimit = "+tt.getUpperLimit()+"; LowerLimit = "+tt.getLowerLimit());
					ttMap.put("status", status);
				}
			}
			
			return toSuccessResult(returnMap);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/compositiveData/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object fdsfdsfe3f(@PathVariable("id") String dfewfcsdfe){
		try{
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			
			List<Transducer> transducers = fdfdfgr5gfg34.fbl(dfewfcsdfe);
			Map<Integer, Float> dataTypeValueMap = new HashMap<Integer, Float>();
			Map<Integer, String> dataTypeMeasureMap = new HashMap<Integer, String>();
			Map<Integer, Integer> dataTypeCountMap = new LinkedHashMap<Integer, Integer>();
			for(Transducer t : transducers){
				if(t.getTransducerDataTypes()==null){
					continue;
				}
				for(TransducerDataType tt : t.getTransducerDataTypes()){
					if(dataTypeMeasureMap.get(tt.getDataType())==null){
						dataTypeMeasureMap.put(tt.getDataType(), tt.getMeasure());
					}
					if(tt.getValue()==null){
						continue;
					}
					if(dataTypeValueMap.get(tt.getDataType())==null){
						dataTypeValueMap.put(tt.getDataType(), tt.getValue());
					}else{
						dataTypeValueMap.put(tt.getDataType(), Float.valueOf(ArithmeticUtil.add(dataTypeValueMap.get(tt.getDataType()), tt.getValue())+""));
					}
					if(dataTypeCountMap.get(tt.getDataType())==null){
						dataTypeCountMap.put(tt.getDataType(), Integer.valueOf(1));
					}else{
						dataTypeCountMap.put(tt.getDataType(), dataTypeCountMap.get(tt.getDataType())+1);
					}
				}
			}
			
			for(Entry<Integer, String> entry : dataTypeMeasureMap.entrySet()){
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("type", entry.getKey());
				map.put("measure", entry.getValue());
				map.put("value", ArithmeticUtil.div(dataTypeValueMap.get(entry.getKey()), dataTypeCountMap.get(entry.getKey()), 2));
				returnList.add(map);
			}
			
			return toSuccessResult(returnList);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
}
