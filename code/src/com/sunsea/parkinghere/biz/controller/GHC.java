package com.sunsea.parkinghere.biz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataType;
import com.sunsea.parkinghere.biz.service.Gdsfdsffrew3fv;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.exception.BizServiceException;
import com.sunsea.parkinghere.framework.utils.ArithmeticUtil;
/**
 * fort
 * @author ylr
 *
 */
@Controller
@RequestMapping(value = "/greenHouse", method = RequestMethod.GET)
public class GHC {
	
	@Autowired
	private Gdsfdsffrew3fv fdsfdsfds;
	@Autowired
	private TS trtewfddf;
	@Autowired
	private SS hghgrewe;

	/**
	 * 大棚
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String erewrewre(ModelMap map){
		return "greenHouse/list";
	}
	
	/**
	 * 大棚
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/new")
	public String vcvcvc(ModelMap map){
		GreenHouse greenHouse = new GreenHouse();
		map.put("data", greenHouse);
		return "greenHouse/edit";
	}
	
	/**
	 * greenhouse
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String fdfd1(@PathVariable String id, ModelMap map){
		GreenHouse greenHouse = fdsfdsfds.fbi(id);
		if(greenHouse==null){
			greenHouse = new GreenHouse();
		}
		map.put("data", greenHouse);
		return "greenHouse/edit";
	}
	
	/**
	 * greenhouse
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String tewfdcs(@PathVariable String id, ModelMap map){
		try{
			this.ewrewsdsd(id, map);
			return "greenHouse/view";
		} catch(Exception e){
			e.printStackTrace();
			throw new BizServiceException(e.getMessage());
		}
	}
	
	/**
	 * wholescreen
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/fullScreenView/{id}")
	public String fdssdfdsfs(@PathVariable String id, ModelMap map){
		try{
			this.ewrewsdsd(id, map);
			return "greenHouse/fullScreenView";
		} catch(Exception e){
			e.printStackTrace();
			throw new BizServiceException(e.getMessage());
		}
	}
	
	private void ewrewsdsd(String id, ModelMap map){
		GreenHouse greenHouse = fdsfdsfds.fbi(id);
		map.put("data", greenHouse);
		List<Transducer> transducers = trtewfddf.fbl(id);
		List<Switch> switchs = hghgrewe.fbl(id);
		List<Map<String, Object>> elementList = new ArrayList<Map<String, Object>>();
		
		Map<Integer, String> measureMap = new HashMap<Integer, String>();
		Map<Integer, String> dataTypeMap = new HashMap<Integer, String>();
		List<Map<String, Object>> averageList = new ArrayList<Map<String, Object>>();
		Map<Integer, Float> average = new HashMap<Integer, Float>();
		Map<Integer, Integer> averageCount = new HashMap<Integer, Integer>();
		for(Transducer transducer : transducers){
			if(transducer.getTransducerDataTypes()==null||transducer.getTransducerDataTypes().length==0){
				continue;
			}
			int count = 0;
			for(TransducerDataType tdt : transducer.getTransducerDataTypes()){
				Map<String, Object> element = new HashMap<String, Object>();
				element.put("id", transducer.getId());
				if(count==0){
					element.put("name", transducer.getName());
				}
				Float value = tdt.getValue();
				element.put("value", value==null?null:(ArithmeticUtil.div(value, 1, 1)+tdt.getMeasure()));
				//element.put("measure", tdt.getMeasure());
				element.put("dataType", tdt.getDataType());
				element.put("type", "transducer");
				element.put("coordinateX", transducer.getCoordinateX());
				element.put("coordinateY", transducer.getCoordinateY()+(count*41.4));
				measureMap.put(tdt.getDataType(), tdt.getMeasure());
				dataTypeMap.put(tdt.getDataType(), tdt.getDataTypeName());
				count++;
				String deviate = "";
				if(tdt.getValue()==null){
					deviate = "N";
				}else if(tdt.getValue()>tdt.getUpperLimit()){
					deviate = "H";
				}else if(tdt.getValue()<tdt.getLowerLimit()){
					deviate = "L";
				}else{
					deviate = "N";
				}
				String icon = tdt.getDataType()+"-"+deviate+".png";
				element.put("icon", icon);
				
				elementList.add(element);
				
				if(average.get(tdt.getDataType())==null){
					average.put(tdt.getDataType(), tdt.getValue());
					averageCount.put(tdt.getDataType(), Integer.valueOf(1));
				}else{
					average.put(tdt.getDataType(), average.get(tdt.getDataType())+tdt.getValue());
					averageCount.put(tdt.getDataType(), averageCount.get(tdt.getDataType())+1);
				}
			}
		}
		for(Switch _switch : switchs){
			Map<String, Object> element = new HashMap<String, Object>();
			element.put("id", _switch.getId());
			element.put("dataType", _switch.getType());
			element.put("type", "switch");
			element.put("value", "");
			element.put("coordinateX", _switch.getCoordinateX());
			element.put("coordinateY", _switch.getCoordinateY());
			String status = "";
			
			if(_switch.getStauts()==null){
				status = "C";
			}else if(_switch.getStauts()==1){
				status = "O";
			}else{
				status = "C";
			}
			String icon = _switch.getType()+"-"+status+".png";
			element.put("icon", icon);
			
			elementList.add(element);
		}
		map.put("elements", elementList);
		
		for(int k=1;k<7;k++){
			if(average.get(Integer.valueOf(k))==null){
				average.put(Integer.valueOf(k), null);
			}
		}
		for(Entry<Integer, Float> entry : average.entrySet()){
			Map<String, Object> element = new HashMap<String, Object>();
			element.put("dataType", entry.getKey());
			if(entry.getValue()==null){
				element.put("value", "");
			}else{
				element.put("value", ArithmeticUtil.div(entry.getValue(), averageCount.get(entry.getKey()), 1));
			}
			element.put("measure", measureMap.get(entry.getKey()));
			element.put("dataTypeName", TransducerDataType.getDataTypeName(entry.getKey()));
			averageList.add(element);
		}
		map.put("averages", averageList);
	}
}
