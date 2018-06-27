package com.sunsea.parkinghere.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.model.TransducerMessage;
import com.sunsea.parkinghere.biz.model.UserMessageReadTime;
import com.sunsea.parkinghere.biz.service.MeSd;
import com.sunsea.parkinghere.module.audit.openapi.AbstractQueryParameter;


@Service
public class MSIfuiwofewi implements MeSd {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public TransducerMessage flnm(String sfsdgsdfe,Long fdsfdsfde) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(sfsdgsdfe));
		if(fdsfdsfde!=null){
			query.addCriteria(Criteria.where("time").gt(fdsfdsfde));
		}
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		return mongoTemplate.findOne(query, TransducerMessage.class);
	}

	@Override
	public List<TransducerMessage> fm(String sfsdgsdfe, Integer fdsfsdfsd, Integer fdsfsdfsd1) {
		AbstractQueryParameter parameter = new AbstractQueryParameter();
		parameter.setLimit(fdsfsdfsd1);
		parameter.setStart(fdsfsdfsd);
		int start = AbstractQueryParameter.getStart(parameter);
        int limit = AbstractQueryParameter.getLimit(parameter);
        PageRequest pageable = new PageRequest(start, limit);
        
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(sfsdgsdfe));
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		query.with(pageable);
		
		return mongoTemplate.find(query, TransducerMessage.class);
	}

	@Override
	public void rm(String fdsf) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(fdsf));
		mongoTemplate.updateFirst(query, Update.update("status", 1), TransducerMessage.class);
	}

	@Override
	public UserMessageReadTime fumrtbui(String sfsdgsdfe) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(sfsdgsdfe));
		return mongoTemplate.findOne(query, UserMessageReadTime.class);
	}

	@Override
	public void srt(Long fdfe33, String fdf, String sfsdgsdfe) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(sfsdgsdfe));
		mongoTemplate.upsert(query, Update.update("readLongTime", fdfe33).set("readStringTime", fdf), UserMessageReadTime.class);
	}
	
}
