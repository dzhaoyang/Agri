package com.sunsea.parkinghere.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import scala.actors.threadpool.Arrays;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sunsea.parkinghere.biz.dto.TfjdisfjsdiDto;
import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataRecord;
import com.sunsea.parkinghere.biz.model.TransducerDataType;
import com.sunsea.parkinghere.biz.model.TransducerMessage;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.module.audit.openapi.AbstractQueryParameter;
/**
 * 传感器服务
 * @author ylr
 *
 */
@Service
public class TSfjdslfsdkI implements TS {
	
	@Autowired
	private MongoTemplate fdse;
	@Autowired
	private SS ferew32;
	@Autowired
	private US ukui87;

	@Override
	public Transducer fbi(String ds) {
		return fdse.findById(ds, Transducer.class);
	}
	
	@Override
	public Transducer fbu(String dsdf) {
		Query query = new Query();
		query.addCriteria(Criteria.where("uuid").is(dsdf));
		List<Transducer> list = fdse.find(query, Transducer.class);
		if(list==null||list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Page<Transducer> fbp(String a, String b, String c, Integer d, Integer e) {
		AbstractQueryParameter parameter = new AbstractQueryParameter();
		parameter.setLimit(e);
		parameter.setStart(d);
		d = AbstractQueryParameter.getStart(parameter);
        e = AbstractQueryParameter.getLimit(parameter);
        PageRequest pageable = new PageRequest(d, e);
        
        Query query = new Query();
        if(StringUtils.isNotBlank(a)){
        	query.addCriteria(Criteria.where("uuid").is(a));
        }
        if(StringUtils.isNotBlank(b)){
        	query.addCriteria(Criteria.where("name").is(b));
        }
        if(StringUtils.isNotBlank(c)){
        	GreenHouse greenHouse = new GreenHouse();
        	greenHouse.setId(c);
        	query.addCriteria(Criteria.where("greenHouse").is(greenHouse));
        }
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        query.with(pageable);
        
        long count = fdse.count(query, Transducer.class);
        List<Transducer> list = fdse.find(query, Transducer.class);
		return new PageImpl<Transducer>(list, pageable, count);
	}

	@Override
	public List<Transducer> fbl() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		return fdse.find(query, Transducer.class);
	}
	
	@Override
	public List<Transducer> fbl(String fdfsd) {
		Query query = new Query();
		GreenHouse greenHouse = new GreenHouse();
		greenHouse.setId(fdfsd);
		query.addCriteria(Criteria.where("greenHouse").is(greenHouse));
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		return fdse.find(query, Transducer.class);
	}
	
	@Override
	public List<Transducer> fbl(String fdfsd,Integer dsfdsg) {
		Query query = new Query();
		GreenHouse greenHouse = new GreenHouse();
		greenHouse.setId(fdfsd);
		query.addCriteria(Criteria.where("greenHouse").is(greenHouse));
		query.addCriteria(Criteria.where("transducerDataTypes.dataType").is(dsfdsg));
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		return fdse.find(query, Transducer.class);
	}

	@Override
	public void dbi(String dsfdsg) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(dsfdsg));
		fdse.remove(query, Transducer.class);
	}
	
	@Override
	public void dbgh(String dsfdsg) {
		Query query = new Query();
		GreenHouse greenHouse = new GreenHouse();
    	greenHouse.setId(dsfdsg);
    	query.addCriteria(Criteria.where("greenHouse").is(greenHouse));
		fdse.remove(query, Transducer.class);
	}

	@Override
	public void serevf312(Transducer dsfdsg) {
		if(StringUtils.isBlank(dsfdsg.getId())){
			fdse.save(dsfdsg);
			return;
		}
		Transducer old = this.fbi(dsfdsg.getId());
		old.setCoordinateX(dsfdsg.getCoordinateX());
		old.setCoordinateY(dsfdsg.getCoordinateY());
		old.setGreenHouse(dsfdsg.getGreenHouse());
		old.setInstallDate(dsfdsg.getInstallDate());
		old.setName(dsfdsg.getName());
		old.setUuid(dsfdsg.getUuid());
		old.setLocation(dsfdsg.getLocation());
		if(old.getTransducerDataTypes()!=null){
			for(TransducerDataType oldDateType : old.getTransducerDataTypes()){
				for(TransducerDataType newDateType : dsfdsg.getTransducerDataTypes()){
					if(oldDateType.getDataType().intValue()==newDateType.getDataType().intValue()){
						oldDateType.setLowerLimit(newDateType.getLowerLimit());
						oldDateType.setLowerLimitCommand(newDateType.getLowerLimitCommand());
						oldDateType.setMeasure(newDateType.getMeasure());
						oldDateType.setIsAuto(newDateType.getIsAuto());
						oldDateType.setUpperLimit(newDateType.getUpperLimit());
						oldDateType.setUpperLimitCommand(newDateType.getUpperLimitCommand());
					}
				}
			}
		}else if(old.getTransducerDataTypes()==null&&dsfdsg.getTransducerDataTypes()!=null){
			old.setTransducerDataTypes(dsfdsg.getTransducerDataTypes());
		}
		
		fdse.save(old);
	}

	@Override
	public void sv(String a, Integer b, Float c, String d) {
		String collectionName = fdse.getCollectionName(Transducer.class);
		DBObject query = new BasicDBObject("_id",new ObjectId(a)).append("transducerDataTypes.dataType", b);
		DBObject update = new BasicDBObject("$set",new BasicDBObject("transducerDataTypes.$.value",c).append("transducerDataTypes.$.lastUpdateTime", d));
		fdse.getCollection(collectionName).update(query, update, false, false);
	}

	@Override
	public List<TfjdisfjsdiDto> f24hrbd(String a, Integer b, String c) {
		
		String collectionName = fdse.getCollectionName(TransducerDataRecord.class);
		String reduce = "function(doc,prev){"
				+ "prev.value += doc.value;"
				+ "prev.count +=1;"
				+ "}";
		Criteria criteria = Criteria.where("dataType").is(b).and("day").is(c).and("transducerId").is(a);
		GroupBy groupBy = GroupBy.key("hour").initialDocument("{value:0,count:0}").reduceFunction(reduce);
		GroupByResults<TfjdisfjsdiDto> gr = fdse.group(criteria, collectionName, groupBy, TfjdisfjsdiDto.class);
		@SuppressWarnings("unchecked")
		List<TfjdisfjsdiDto> list = IteratorUtils.toList(gr.iterator());
		for(TfjdisfjsdiDto t24 : list){
			t24.setValue(t24.getValue()/t24.getCount());
		}
		return std24hbdbh(list);
	}

	public List<TfjdisfjsdiDto> f24hrbd(String dffdsf, String dfsf) {
		String collectionName = fdse.getCollectionName(TransducerDataRecord.class);
		String reduce = "function(doc,prev){"
				+ "prev.value += doc.value;"
				+ "prev.count +=1;"
				+ "}";
		Criteria criteria = Criteria.where("transducerId").is(dffdsf).and("day").is(dfsf);
		
		GroupBy groupBy = GroupBy.key("hour","dataType").initialDocument("{value:0,count:0}").reduceFunction(reduce);
		GroupByResults<TfjdisfjsdiDto> gr = fdse.group(criteria, collectionName, groupBy, TfjdisfjsdiDto.class);
		@SuppressWarnings("unchecked")
		List<TfjdisfjsdiDto> list = IteratorUtils.toList(gr.iterator());
		for(TfjdisfjsdiDto t24 : list){
			t24.setValue(t24.getValue()/t24.getCount());
		}
		return std24hbdbh(list);
	}

	@Override
	public List<TfjdisfjsdiDto> fdrbat(String a, String b, String c) {;
		String collectionName = fdse.getCollectionName(TransducerDataRecord.class);
		String reduce = "function(doc,prev){"
				+ "prev.value += doc.value;"
				+ "prev.count +=1;"
				+ "if(prev.maxValue<doc.value){"
				 + "prev.maxValue = doc.value;"
				+ "}"
				+ "if(prev.minValue>doc.value){"
				 + "prev.minValue = doc.value;"
				+ "}"
				+ "}";
		Criteria criteria = Criteria.where("transducerId").is(a).and("sourceTime").gte(b).lte(c);
		GroupBy groupBy = GroupBy.key("day","dataType").initialDocument("{value:0,count:0,maxValue:-1000000000,minValue:1000000000}").reduceFunction(reduce);
		GroupByResults<TfjdisfjsdiDto> gr = fdse.group(criteria, collectionName, groupBy, TfjdisfjsdiDto.class);
		@SuppressWarnings("unchecked")
		List<TfjdisfjsdiDto> list = IteratorUtils.toList(gr.iterator());
		for(TfjdisfjsdiDto t24 : list){
			t24.setValue(t24.getValue()/t24.getCount());
		}
		return std24hbdbd(list);
	}
	
	@Override
	public List<TfjdisfjsdiDto> fdrbdt(String a, String b, String c, String d, String e) {
		String collectionName = fdse.getCollectionName(TransducerDataRecord.class);
		String reduce = "function(doc,prev){"
				+ "prev.value += doc.value;"
				+ "prev.count +=1;"
				+ "if(prev.maxValue<doc.value){"
				 + "prev.maxValue = doc.value;"
				+ "}"
				+ "if(prev.minValue>doc.value){"
				 + "prev.minValue = doc.value;"
				+ "}"
				+ "}";
		Criteria criteria = Criteria.where("dataType").is(Integer.valueOf(c)).and("sourceTime").gte(d).lte(e);
		if(StringUtils.isNotBlank(b)){
			criteria.and("transducerId").is(b);
		}else if(StringUtils.isNotBlank(a)){
			List<Transducer> list = this.fbl(a);
			if(!list.isEmpty()){
				List<String> transducerIds = new ArrayList<>(list.size());
				for(Transducer t : list){
					transducerIds.add(t.getId());
				}
				criteria.and("transducerId").in(transducerIds);
			}
		}
		GroupBy groupBy = GroupBy.key("day").initialDocument("{value:0,count:0,maxValue:-1000000000,minValue:1000000000}").reduceFunction(reduce);
		GroupByResults<TfjdisfjsdiDto> gr = fdse.group(criteria, collectionName, groupBy, TfjdisfjsdiDto.class);
		@SuppressWarnings("unchecked")
		List<TfjdisfjsdiDto> list = IteratorUtils.toList(gr.iterator());
		for(TfjdisfjsdiDto t24 : list){
			t24.setValue(t24.getValue()/t24.getCount());
		}
		return std24hbdbd(list);
	}

	@Override
	public void ctdr(TransducerDataRecord fdsf) {
		fdse.save(fdsf);
	}

	@Override
	public void jadaa(String a, Integer b, Float c) {
		Transducer transducer = this.fbi(a);
		if(transducer==null){
			return;
		}
		if(transducer.getTransducerDataTypes()==null){
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(TransducerDataType tt : transducer.getTransducerDataTypes()){
			if(tt.getDataType().intValue()==b.intValue()){
				// 判断并创建告警消息
				String title = "";
				String content = "";
				String limitCommand = "";
				Integer transducerStatus = null;
				if(c<tt.getLowerLimit()){
					title = "告警";
					content = transducer.getGreenHouse().getName()+"的"+this.dfger5(tt.getDataType())+"传感器当前值为"+c+tt.getMeasure()+"，已低于阈值下限，敬请注意！";
					limitCommand = tt.getLowerLimitCommand();
					transducerStatus = -1;
				}else if(c>tt.getUpperLimit()){
					title = "告警";
					content = transducer.getGreenHouse().getName()+"的"+this.dfger5(tt.getDataType())+"传感器当前值为"+c+tt.getMeasure()+"，已高于阈值上限，敬请注意！";
					limitCommand = tt.getUpperLimitCommand();
					transducerStatus = 1;
				}else{
					return;
				}
				List<User> users = this.ukui87.findAll();
				List<TransducerMessage> transducerMessages = new ArrayList<>();
				for(User user : users){
					TransducerMessage tm = new TransducerMessage();
					tm.setTransducerId(a);
					tm.setTransducerType(transducer.getType());
					tm.setTime(System.currentTimeMillis());
					tm.setStatus(0);
					tm.setTitle(title);
					tm.setContent(content);
					tm.setTransducerStatus(transducerStatus);
					tm.setUserId(user.getId());
					tm.setCreateTime(sdf.format(new Date()));
					transducerMessages.add(tm);
				}
				/*mongoTemplate.save(transducerMessages);*/
				fdse.insertAll(transducerMessages);
				
				//如果传感器启动自动触发开关的执行操作就执行
				try{
					if(tt.getIsAuto()!=null&&tt.getIsAuto().intValue()==1){
						Map<String, Integer> commandMap = new LinkedHashMap<String, Integer>();
						String[] commands = limitCommand.split("\r\n");
						for(String command : commands){
							String[] coms = command.split("=");
							if(coms.length!=2){
								return;
							}
							commandMap.put(coms[0], Integer.valueOf(coms[1]));
						}
						
						ferew32.elc(commandMap);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public String fmbdt(Integer dataType) {
		String collectionName = fdse.getCollectionName(Transducer.class);
		DBObject query = new BasicDBObject("transducerDataTypes.dataType", dataType);
		DBObject fields = new BasicDBObject("transducerDataTypes.measure",1);
		DBObject result = fdse.getCollection(collectionName).findOne(query, fields);
		BasicDBList list = (BasicDBList)result.get("transducerDataTypes");
		String measure = ((BasicDBObject)list.get(0)).getString("measure");
		return measure;
	}
	
	
	private String dfger5(int gfb){
		switch (gfb) {
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
	
	private List<TfjdisfjsdiDto> std24hbdbh(List<TfjdisfjsdiDto> fdscc){
		TfjdisfjsdiDto aa;
		TfjdisfjsdiDto[] dd = fdscc.toArray(new TfjdisfjsdiDto[]{});
		for(int i=0;i<dd.length;i++){
			for(int k=0;k<dd.length-1;k++){
				if(dd[k].getHour().compareTo(dd[k+1].getHour())>0){
					aa = dd[k+1];
					dd[k+1] = dd[k];
					dd[k] = aa;
				}
			}
		}
		return Arrays.asList(dd);
	}
	
	private List<TfjdisfjsdiDto> std24hbdbd(List<TfjdisfjsdiDto> fdsfc){
		TfjdisfjsdiDto aa;
		TfjdisfjsdiDto[] dd = fdsfc.toArray(new TfjdisfjsdiDto[]{});
		for(int i=0;i<dd.length;i++){
			for(int k=0;k<dd.length-1;k++){
				if(dd[k].getDay().compareTo(dd[k+1].getDay())>0){
					aa = dd[k+1];
					dd[k+1] = dd[k];
					dd[k] = aa;
				}
			}
		}
		return Arrays.asList(dd);
	}

}
