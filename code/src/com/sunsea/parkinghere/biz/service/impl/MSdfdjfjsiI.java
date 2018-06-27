package com.sunsea.parkinghere.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.biz.model.Menu;
import com.sunsea.parkinghere.biz.service.MS;
/**
 * 菜单服务
 * @author ylr
 *
 */
@Service
public class MSdfdjfjsiI implements MS {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void cm(Menu fd) {
		mongoTemplate.save(fd);
	}

	@Override
	public List<Menu> fsm(String fd) {
		Query query = new Query();
		Menu parentMenu = new Menu();
		parentMenu.setId(fd);
		query.addCriteria(Criteria.where("parentMenu").is(parentMenu));
		
		query.with(new Sort(Sort.Direction.ASC,"sort"));
		return mongoTemplate.find(query, Menu.class);
	}

	@Override
	public List<Menu> ftm() {
		Query query = new Query();
		query.addCriteria(Criteria.where("level").is(1));

		return mongoTemplate.find(query, Menu.class);
	}

	@Override
	public void dbi(String fd) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(fd));
		mongoTemplate.remove(query, Menu.class);
	}

	@Override
	public Menu fbn(String fd) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(fd));
		return mongoTemplate.findOne(query, Menu.class);
	}

	@Override
	public void im() {
		List<Menu> menus = new ArrayList<Menu>();
		Menu tmenu = new Menu();
		tmenu.setLevel(1);
		tmenu.setName("实时监控");
		tmenu.setSort(0);
		menus.add(tmenu);
		
		tmenu = new Menu();
		tmenu.setLevel(1);
		tmenu.setName("数据分析");
		tmenu.setSort(1);
		menus.add(tmenu);
		
		tmenu = new Menu();
		tmenu.setLevel(1);
		tmenu.setName("基础数据");
		tmenu.setSort(2);
		menus.add(tmenu);
		
		tmenu = new Menu();
		tmenu.setLevel(1);
		tmenu.setName("系统管理");
		tmenu.setSort(3);
		menus.add(tmenu);
		mongoTemplate.insertAll(menus);
		
		List<Menu> smenus = new ArrayList<Menu>();
		tmenu = this.fbn("数据分析");
		Menu smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("二氧化碳");
		smenu.setParentMenu(tmenu);
		smenu.setSort(0);
		smenu.setUrl("");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("PM2.5");
		smenu.setParentMenu(tmenu);
		smenu.setSort(1);
		smenu.setUrl("");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("土壤温度");
		smenu.setParentMenu(tmenu);
		smenu.setSort(2);
		smenu.setUrl("");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("棚内温度");
		smenu.setParentMenu(tmenu);
		smenu.setSort(3);
		smenu.setUrl("");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("空气湿度");
		smenu.setParentMenu(tmenu);
		smenu.setSort(4);
		smenu.setUrl("");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("光照度");
		smenu.setParentMenu(tmenu);
		smenu.setSort(5);
		smenu.setUrl("");
		smenus.add(smenu);
		//////////////////////////////////////////////
		tmenu = this.fbn("基础数据");
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("大棚管理");
		smenu.setParentMenu(tmenu);
		smenu.setSort(0);
		smenu.setUrl("/greenHouse/list");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("传感器管理");
		smenu.setParentMenu(tmenu);
		smenu.setSort(1);
		smenu.setUrl("/transducer/list");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("开关管理");
		smenu.setParentMenu(tmenu);
		smenu.setSort(2);
		smenu.setUrl("/switch/list");
		smenus.add(smenu);
		//////////////////////////////////////////
		tmenu = this.fbn("系统管理");
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("用户管理");
		smenu.setParentMenu(tmenu);
		smenu.setSort(0);
		smenu.setUrl("/dashboard/identity/users");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("角色管理");
		smenu.setParentMenu(tmenu);
		smenu.setSort(1);
		smenu.setUrl("/dashboard/identity/roles");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("审计操作");
		smenu.setParentMenu(tmenu);
		smenu.setSort(2);
		smenu.setUrl("/dashboard/audits");
		smenus.add(smenu);
		
		smenu = new Menu();
		smenu.setLevel(2);
		smenu.setName("操作日志");
		smenu.setParentMenu(tmenu);
		smenu.setSort(3);
		smenu.setUrl("/dashboard/authentications");
		smenus.add(smenu);
		
		mongoTemplate.insertAll(smenus);
	}

	@Override
	public List<Menu> fsm() {
		Query query = new Query();
		query.addCriteria(Criteria.where("level").is(2));
		
		query.with(new Sort(Sort.Direction.ASC,"id"));
		return mongoTemplate.find(query, Menu.class);
	}

	@Override
	public void un(String sde, String df) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(sde));
		
		mongoTemplate.updateFirst(query, Update.update("name", df), Menu.class);
	}
}
