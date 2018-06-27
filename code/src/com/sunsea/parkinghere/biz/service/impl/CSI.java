package com.sunsea.parkinghere.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sunsea.parkinghere.biz.model.Customer;
import com.sunsea.parkinghere.biz.service.Cftgdfhyh6hgf;
@Service
public class CSI implements Cftgdfhyh6hgf {
	@Autowired
	private MongoTemplate mongoTemplate;

	
	public void c3(Customer sdfd) {
		mongoTemplate.save(sdfd);
	}

	
	public Customer c6(String sdfd) {
		return mongoTemplate.findById(sdfd, Customer.class);
	}

	
	public void c10(Customer sdfd) {
		mongoTemplate.save(sdfd);
	}


	
	public List<Customer> c7(String arg) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(arg));
		return mongoTemplate.find(query, Customer.class);
	}


	
	public long c4() {
		Query query = new Query();
		return mongoTemplate.count(query, Customer.class);
	}


	
	public List<String> c5() {
		String collectionName = mongoTemplate.getCollectionName(Customer.class);
		BasicDBObject ref = new BasicDBObject();
		BasicDBObject keys = new BasicDBObject("phone",1);
		
		List<String> list = new ArrayList<String>();
		DBCursor bc = mongoTemplate.getCollection(collectionName).find(ref, keys);
		while(bc.hasNext()){
			DBObject obj = bc.next();
			list.add(obj.get("phone").toString());
		}
		return list;
	}


	
	public long c8(Integer age1, Integer age2) {
		Query query = new Query();
		if(age1!=null&&age2==null){
			query.addCriteria(Criteria.where("age").gte(age1));
		}else if(age1==null&&age2!=null){
			query.addCriteria(Criteria.where("age").lte(age2));
		}else if(age1!=null&&age2!=null){
			query.addCriteria(Criteria.where("age").gte(age1).lte(age2));
		}
		return mongoTemplate.count(query, Customer.class);
	}


	
	public List<String> c9(Integer age1, Integer age2) {
		String collectionName = mongoTemplate.getCollectionName(Customer.class);
		BasicDBObject ref = new BasicDBObject();
		BasicDBObject keys = new BasicDBObject("phone",1);
		if(age1!=null&&age2==null){
			ref.append("age", new BasicDBObject("$gte",age1));
		}else if(age1==null&&age2!=null){
			ref.append("age", new BasicDBObject("$lte",age2));
		}else if(age1!=null&&age2!=null){
			ref.append("age", new BasicDBObject[]{new BasicDBObject("$gte",age1),new BasicDBObject("$lte",age2)});
		}
		
		List<String> list = new ArrayList<String>();
		DBCursor bc = mongoTemplate.getCollection(collectionName).find(ref, keys);
		while(bc.hasNext()){
			DBObject obj = bc.next();
			list.add(obj.get("phone").toString());
		}
		return list;
	}


	
	public void c1(String arg, int crg) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(arg));
		mongoTemplate.updateMulti(query, new Update().inc("count", 1), Customer.class);
	}


	
	public void c2(String fds) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(fds));
		mongoTemplate.updateMulti(query, Update.update("count", 0), Customer.class);
	}

}
