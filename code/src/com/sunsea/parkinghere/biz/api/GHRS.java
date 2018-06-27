package com.sunsea.parkinghere.biz.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.gridfs.GridFSFile;
import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataType;
import com.sunsea.parkinghere.biz.service.Gdsfdsffrew3fv;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.framework.utils.ArithmeticUtil;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Auditable
@Controller
@RequestMapping("/api/greenHouse")
public class GHRS extends AbstractRestService {

	@Autowired
	private Gdsfdsffrew3fv fsdfsd;
	@Autowired
	private TS sertyuighjkvbnrty;
	@Autowired
	private SS kjh;
	
	@Auditable
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
	public Object fdsae45(Integer start,Integer limit){
		try{
			start = start==null?0:start;
			limit = limit==null?20:limit;
			Page<GreenHouse> page = fsdfsd.fbp(start, limit);
			return WebUtils.succeedMap(page);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@Auditable
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public Object dfsdfd(@PathVariable("id") String dsfdsfdd){
		try{
			fsdfsd.gd(dsfdsfdd);
			return WebUtils.succeedMap();
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@Auditable
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object fdsad3(GreenHouse fdsv,HttpServletRequest jhgf,HttpServletResponse xdse) throws IOException {
		GridFSFile gfsFile = null;
		try{
			gfsFile = kjhgfdsrtyu(jhgf, xdse, null);
			if(gfsFile!=null){
				if(StringUtils.isNotBlank(fdsv.getId())){
					GreenHouse old = fsdfsd.fbi(fdsv.getId());
					if(StringUtils.isNotBlank(old.getMapPhotoFileName())){
						try{
							fdfdsw.removeByFileName(old.getMapPhotoFileName());
						}catch(Exception e){}
					}
				}
				@SuppressWarnings("unchecked")
				Map<String, Object> metaData = gfsFile.getMetaData().toMap();
				fdsv.setMapHeight(Integer.decode(metaData.get("height").toString()));
				fdsv.setMapWidth(Integer.decode(metaData.get("width").toString()));
				fdsv.setMapPhotoFileId(gfsFile.getId().toString());
				fdsv.setMapPhotoFileName(gfsFile.getFilename());
				fdsv.setMapOriginalFileName((String)metaData.get("originalFileName"));
				fdsv.setMapUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				fdsv.setVarsion(fdsv.getVarsion()==null?1:(fdsv.getVarsion()+1));
			}
			if(StringUtils.isBlank(fdsv.getId()) ){
				fdsv.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
			fsdfsd.s(fdsv);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", fdsv.getId());
			return WebUtils.succeedMap(map);
		}catch(Exception e){
			e.printStackTrace();
			if(gfsFile!=null){
				fdfdsw.removeByFileName(gfsFile.getFilename());
			}
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	
	@Auditable
    @RequestMapping(value = "/deleteMap/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object dm(@PathVariable("id") String fdsfd) {
		try{
			GreenHouse greenHouse = fsdfsd.fbi(fdsfd);
			String mapPhotoFileName = greenHouse.getMapPhotoFileName();
			greenHouse.setMapHeight(null);
			greenHouse.setMapWidth(null);
			greenHouse.setMapPhotoFileId(null);
			greenHouse.setMapPhotoFileName(null);
			greenHouse.setMapOriginalFileName(null);
			greenHouse.setMapUpdateTime(null);
			fsdfsd.s(greenHouse);
			try{
				fdfdsw.removeByFileName(mapPhotoFileName);
			}catch(Exception e){}
	        return WebUtils.succeedMap();
		}catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
    }
	
	@RequestMapping(value = "/current/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object fdsfsdf(@PathVariable("id") String fdsf) {
		try{
			List<Transducer> transducers = sertyuighjkvbnrty.fbl(fdsf);
			List<Switch> switchs = kjh.fbl(fdsf);
			
			Map<String, Object> returnMap = new HashMap<String, Object>();
			List<Map<String, Object>> elementList = new ArrayList<Map<String, Object>>();
			Map<Integer, String> measureMap = new HashMap<Integer, String>();
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
					element.put("dataType", tdt.getDataType());
					element.put("type", "transducer");
					Float value = tdt.getValue();
					element.put("value", value==null?null:(ArithmeticUtil.div(value, 1, 1)+tdt.getMeasure()));
					measureMap.put(tdt.getDataType(), tdt.getMeasure());
					element.put("coordinateX", transducer.getCoordinateX());
					element.put("coordinateY", transducer.getCoordinateY()+(count*41.4));
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
					
					if(tdt.getValue()==null){
						continue;
					}
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
			
			for(int k=1;k<7;k++){
				if(average.get(Integer.valueOf(k))==null){
					average.put(Integer.valueOf(k), null);
				}
			}
			for(Entry<Integer, Float> entry : average.entrySet()){
				if(entry.getValue()==null){
					average.put(entry.getKey(), null);
				}else{
					average.put(entry.getKey(), Float.valueOf(ArithmeticUtil.div(entry.getValue(), averageCount.get(entry.getKey()), 1)+""));
				}
			}
			returnMap.put("elementList", elementList);
			returnMap.put("average", average);
			returnMap.put("measureMap", measureMap);
			
	        return WebUtils.succeedMap(returnMap);
		}catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
    } 
	
}
