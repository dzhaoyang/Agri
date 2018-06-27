package com.sunsea.parkinghere.biz.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataType;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Auditable
@Controller
@RequestMapping("/api/transducer")
public class TRSSkfenwo {
	
	@Autowired
	private TS cvddsffdsfdsfds;
	

	@Auditable
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
	public Object yuibgjfhnj(String uuid,String name,String greenHouseId,Integer start,Integer limit){
		try{
			start = start==null?0:start;
			limit = limit==null?20:limit;
			Page<Transducer> page = cvddsffdsfdsfds.fbp(uuid, name, greenHouseId, start, limit);
			return WebUtils.succeedMap(page);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@Auditable
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public Object fdsafd(@PathVariable("id") String dsssssssssss){
		try{
			cvddsffdsfdsfds.dbi(dsssssssssss);
			return WebUtils.succeedMap();
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@Auditable
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
	public Object sdfghjklmnbvcxzqertyuiop(Transducer sdfghjiuytrew){
		try{
			
			sdfghjiuytrew.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			TransducerDataType[] transducerDataTypes = new TransducerDataType[1];
			TransducerDataType transducerDataType = new TransducerDataType();
			
			switch (sdfghjiuytrew.getType().intValue()) {
			case 1://
				transducerDataTypes = new TransducerDataType[4];
				
				transducerDataType.setDataType(2);
				transducerDataType.setDataTypeName("PM2.5");
				transducerDataType.setIsAuto(0);
				transducerDataTypes[0] = transducerDataType;
				
				transducerDataType = new TransducerDataType();
				transducerDataType.setDataType(1);
				transducerDataType.setDataTypeName("二氧化碳");
				transducerDataType.setIsAuto(0);
				transducerDataTypes[1] = transducerDataType;
				
				transducerDataType = new TransducerDataType();
				transducerDataType.setDataType(4);
				transducerDataType.setDataTypeName("空气温度");
				transducerDataType.setIsAuto(0);
				transducerDataTypes[2] = transducerDataType;
				
				transducerDataType = new TransducerDataType();
				transducerDataType.setDataType(6);
				transducerDataType.setDataTypeName("空气湿度");
				transducerDataType.setIsAuto(0);
				transducerDataTypes[3] = transducerDataType;
				
				sdfghjiuytrew.setTransducerDataTypes(transducerDataTypes);
				break;
			case 2://
				transducerDataType.setDataType(3);
				transducerDataType.setDataTypeName("土壤湿度");
				transducerDataType.setIsAuto(0);
				transducerDataTypes[0] = transducerDataType;
				sdfghjiuytrew.setTransducerDataTypes(transducerDataTypes);
				break;
			case 3://
				transducerDataType.setDataType(5);
				transducerDataType.setDataTypeName("光照度");
				transducerDataType.setIsAuto(0);
				transducerDataTypes[0] = transducerDataType;
				sdfghjiuytrew.setTransducerDataTypes(transducerDataTypes);
				break;
			default:
				break;
			}

			cvddsffdsfdsfds.serevf312(sdfghjiuytrew);
			Map<String, String> map = new HashMap<String, String>(1);
			map.put("id", sdfghjiuytrew.getId());
			return WebUtils.succeedMap(map);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	
	@Auditable
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
	public Object qazwedcvfrtgbhyujm(Transducer plkmojnbuh){
		try{
			cvddsffdsfdsfds.serevf312(plkmojnbuh);
			Map<String, String> map = new HashMap<String, String>(1);
			map.put("id", plkmojnbuh.getId());
			return WebUtils.succeedMap(map);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
    @RequestMapping(value = "/currentAverage/{greenHouseId}", method = RequestMethod.GET)
    @ResponseBody
	public Object cvcvcxvcxvcx(@PathVariable("greenHouseId") String greenHouseId){
		try{
			List<Transducer> list = cvddsffdsfdsfds.fbl(greenHouseId);
			Map<Integer, Object> map = new HashMap<Integer, Object>();
			for(Transducer t : list){
				if(t.getTransducerDataTypes()==null){
					continue;
				}
				for(TransducerDataType tt : t.getTransducerDataTypes()){
					if(tt.getValue()!=null){
						map.put(tt.getDataType(), tt.getValue().toString()+" "+tt.getMeasure());
					}
				}
			}
			return WebUtils.succeedMap(map);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}

}
