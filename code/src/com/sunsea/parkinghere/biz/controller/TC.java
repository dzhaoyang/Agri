package com.sunsea.parkinghere.biz.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.biz.dto.TfjdisfjsdiDto;
import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataType;
import com.sunsea.parkinghere.biz.service.Gdsfdsffrew3fv;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.exception.BizServiceException;
import com.sunsea.parkinghere.framework.utils.TimeUtils;


@Controller
@RequestMapping(value = "/transducer", method = RequestMethod.GET)
public class TC {
	
	@Autowired
	private TS fdfdsfdsf32gfd4;
	@Autowired
	private Gdsfdsffrew3fv fsdfdfser31;

	
	@RequestMapping(value = "/list")
	public String dfsfdf324fsd(ModelMap map){
		List<GreenHouse> greenHouses = fsdfdfser31.fbl();
		map.addAttribute("greenHouses", greenHouses);
		return "transducer/list";
	}
	
	
	@RequestMapping(value = "/new")
	public String ytrewqanbvcx45(ModelMap map){
		Transducer transducer = new Transducer();
		map.put("data", transducer);
		List<GreenHouse> greenHouses = fsdfdfser31.fbl();
		map.addAttribute("greenHouses", greenHouses);
		return "transducer/edit";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id,ModelMap map){
		Transducer transducer = fdfdsfdsf32gfd4.fbi(id);
		String typestr = "";
		
		if(transducer==null){
			transducer = new Transducer();
		}else{
			if(transducer.getType()==1){
	        	typestr = "PM2.5传感器";
	        }else if(transducer.getType()==2){
	        	typestr = "土壤传感器";
	        }else if(transducer.getType()==3){
	        	typestr = "光照传感器";
	        }
		}
		map.put("data", transducer);
		List<GreenHouse> greenHouses = fsdfdfser31.fbl();
		map.addAttribute("greenHouses", greenHouses);
		map.addAttribute("typestr", typestr);
        
		return "transducer/edit";
	}
	
	
	@RequestMapping(value = "/monitorToday/{id}/{dataType}")
	public String wertyuijhgfdswty34(@PathVariable String id, @PathVariable Integer dataType, ModelMap map){
		try{
			Transducer transducer = fdfdsfdsf32gfd4.fbi(id);
			TransducerDataType tdt = null;
			if(transducer.getType()==1){
				for(TransducerDataType tt : transducer.getTransducerDataTypes()){
					if(tt.getDataType().intValue()==dataType.intValue()){
						tdt = tt;
					}
				}
			}else{
				tdt = transducer.getTransducerDataTypes()[0];
			}
			
			Map<String, Object> returnMap = new HashMap<String, Object>();
			String name = "";

			if(transducer.getType()==1){
				name = "PM2.5传感器("+tdt.getDataTypeName()+")";
	        }else if(transducer.getType()==2){
	        	name = "土壤传感器("+tdt.getDataTypeName()+")";
	        }else if(transducer.getType()==3){
	        	name = "光照传感器("+tdt.getDataTypeName()+")";
	        }
			name = name+"-"+transducer.getName();
			
			returnMap.put("name", name);
			returnMap.put("value", tdt.getValue());
			returnMap.put("lastUpdateTime", tdt.getLastUpdateTime());
			returnMap.put("lowerLimit", tdt.getLowerLimit());
			returnMap.put("upperLimit", tdt.getUpperLimit());
			returnMap.put("measure", tdt.getMeasure());
			returnMap.put("greenHouseId", transducer.getGreenHouse().getId());
			returnMap.put("transducerId", transducer.getId());
			
			map.put("data", returnMap);
			
			List<TfjdisfjsdiDto> list = fdfdsfdsf32gfd4.f24hrbd(id, dataType, TimeUtils.yyyy_MM_dd.format(new Date()));
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			
			for(TfjdisfjsdiDto h24 : list){
				Map<String, Object> t24 = new HashMap<String, Object>();
				t24.put("hour", h24.getHour());
				t24.put("value", h24.getValue()+"");
				returnList.add(t24);
			}
			map.put("details", list);
			map.put("dataType", dataType);
			return "transducer/monitorToday";
		}catch(Exception e){
			e.printStackTrace();
			throw new BizServiceException(e.getMessage());
		}
	}
	
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/analysis/{dataType}")
	public String fdsdsgdg43532fds(@PathVariable Integer dataType, String startTime, String endTime, 
			String measure, String greenHouseId, String transducerId, ModelMap map){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StringUtils.isBlank(endTime)){
				endTime = sdf.format(new Date());
			}
			if(endTime.length()==10){
				endTime += " 23:59:59";
			}
			if(StringUtils.isBlank(startTime)){
				Calendar calc =Calendar.getInstance(); 
				calc.setTime(new Date());  
	            calc.add(calc.DATE, -30);  
	            Date minDate = calc.getTime();  
	            startTime = sdf.format(minDate);
	            //startTime += " 00:00:00";
			}
			if(startTime.length()==10){
				startTime += " 23:59:59";
			}
			
			greenHouseId = StringUtils.isBlank(greenHouseId)?"5980a386cc4ba1d1aeeddba7":greenHouseId;
			
			measure = StringUtils.isNotBlank(measure)?measure:fdfdsfdsf32gfd4.fmbdt(dataType);
			List<TfjdisfjsdiDto> list = fdfdsfdsf32gfd4.fdrbdt(greenHouseId, transducerId, dataType.toString(), startTime, endTime);
			map.put("details", list);
			map.put("dataType", dataType);
			map.put("measure", measure);
			map.put("dataTypeName", this.fdsfasdfdsfd(dataType));
			map.put("greenHouseId", greenHouseId);
			map.put("transducerId", transducerId);
			
			String titleName = "";
			
			List<GreenHouse> greenHouses = fsdfdfser31.fbl();
			map.addAttribute("greenHouses", greenHouses);
			if(StringUtils.isNotBlank(greenHouseId)){
				for(GreenHouse greenHouse : greenHouses){
					if(greenHouse.getId().equals(greenHouseId)){
						titleName += greenHouse.getName();
					}
				}
			}
			
			if(StringUtils.isNotBlank(transducerId)){
				Transducer transducer = this.fdfdsfdsf32gfd4.fbi(transducerId);
				if(transducer!=null){
					map.put("transducerName", transducer.getName());
					titleName += " > "+transducer.getName();
				}
			}else{
				List<Transducer> transducers = fdfdsfdsf32gfd4.fbl(greenHouseId, dataType);
				titleName += " > ";
				for(Transducer t : transducers){
					titleName += t.getName()+"、";
				}
				titleName = titleName.substring(0, titleName.length()-1);
			}
			map.put("titleName", titleName);
			
			return "transducer/analysis";
		}catch(Exception e){
			e.printStackTrace();
			throw new BizServiceException(e.getMessage());
		}
	}
	
	
	private String fdsfasdfdsfd(int fdfd){
		switch (fdfd) {
		case 1:
			return "二氧化碳";
		case 2:
			return "PM2.5";
		case 3:
			return "土壤湿度";
		case 4:
			return "空气温度";
		case 5:
			return "光照度";
		case 6:
			return "空气湿度";
		default:
			return "";
		}
	}
}
