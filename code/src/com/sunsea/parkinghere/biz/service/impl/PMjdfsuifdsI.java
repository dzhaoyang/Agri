package com.sunsea.parkinghere.biz.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sunsea.parkinghere.biz.service.PMRS;
/**
 * 密码修改记录服务
 * @author ym
 *
 */
@Service
public class PMjdfsuifdsI implements PMRS {

	@Autowired
	private MongoTemplate mongoTemplate;

	
	@SuppressWarnings({"rawtypes"})
	public Map<String, Object> fmc(String userId, String date, String type) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		DBObject o = new BasicDBObject("userId", userId).append("date", date).append("type", type);
		DBObject fields = new BasicDBObject("_id", 1).append("count", 1);
		DBObject result = mongoTemplate.getCollection(cn).findOne(o, fields);
		if(result!=null){
			Map map = result.toMap();
			if(map!=null&&!map.isEmpty()){
				returnMap.put("id", map.get("_id"));
				returnMap.put("count", map.get("count"));
			}
		}
		return returnMap;
	}

	
	public void an(String id) {
		DBObject query = new BasicDBObject("_id", new ObjectId(id));
		DBObject update = new BasicDBObject("$inc", new BasicDBObject("count", 1));
		mongoTemplate.getCollection(cn).update(query, update, false, true);
	}

	
	public void cr(String userId, String date, String type) {
		DBObject arr = new BasicDBObject("userId", userId).append("date", date).append("type", type).append("count", 1);
		mongoTemplate.getCollection(cn).insert(arr);
	}
	
	
}
