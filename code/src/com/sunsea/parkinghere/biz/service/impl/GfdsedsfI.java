package com.sunsea.parkinghere.biz.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.common.UserRoleMenuMapping;
import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Menu;
import com.sunsea.parkinghere.biz.service.Gdsfdsffrew3fv;
import com.sunsea.parkinghere.biz.service.MS;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.module.audit.openapi.AbstractQueryParameter;


@Service
public class GfdsedsfI implements Gdsfdsffrew3fv {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private TS fdfdts;
	
	@Autowired
	private MS fdsams;

	@Override
	public GreenHouse fbi(String a) {
		return mongoTemplate.findById(a, GreenHouse.class);
	}

	@Override
	public Page<GreenHouse> fbp(Integer a, Integer b) {
		AbstractQueryParameter parameter = new AbstractQueryParameter();
		parameter.setLimit(b);
		parameter.setStart(a);
		a = AbstractQueryParameter.getStart(parameter);
        b = AbstractQueryParameter.getLimit(parameter);
        PageRequest pageable = new PageRequest(a, b);
        
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        query.with(pageable);
        
        long count = mongoTemplate.count(query, GreenHouse.class);
        List<GreenHouse> list = mongoTemplate.find(query, GreenHouse.class);
		return new PageImpl<GreenHouse>(list, pageable, count);
	}

	@Override
	public List<GreenHouse> fbl() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC,"createTime"));
		return mongoTemplate.find(query, GreenHouse.class);
	}

	@Override
	public void gd(String dd) {
		GreenHouse dsgsf = this.fbi(dd);
		if(dsgsf==null){
			return;
		}
		mongoTemplate.remove(dsgsf);
		this.dm(dsgsf.getMenuId());
	}

	@Override
	public void s(GreenHouse dsgsf) {
		if(StringUtils.isBlank(dsgsf.getId())){
			mongoTemplate.save(dsgsf);
			String menuId = this.cnm(dsgsf);
			dsgsf.setMenuId(menuId);
		}else{
			GreenHouse old = this.fbi(dsgsf.getId());
			if(!old.getName().equals(dsgsf.getName())){
				this.umn(dsgsf.getMenuId(), dsgsf.getName());
			}
		}
		mongoTemplate.save(dsgsf);
	}
	
	
	private String cnm(GreenHouse dsgsf){
		Menu menu = new Menu();
		menu.setLevel(2);
		menu.setName(dsgsf.getName());
		menu.setUrl("/greenHouse/view/"+dsgsf.getId());
		menu.setSort(1);
		
		menu.setParentMenu(fdsams.fbn("实时监控"));
		fdsams.cm(menu);
		
		UserRoleMenuMapping.clearAll();
		
		return menu.getId();
	}
	
	
	private void umn(String dsfds, String fdgdsf){
		fdsams.un(dsfds, fdgdsf);
		UserRoleMenuMapping.clearAll();
	}
	
	
	private void dm(String fdsfsd){
		fdsams.dbi(fdsfsd);
		UserRoleMenuMapping.clearAll();
	}
}
