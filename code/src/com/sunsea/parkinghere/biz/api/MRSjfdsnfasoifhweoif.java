package com.sunsea.parkinghere.biz.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sunsea.parkinghere.biz.common.UserRoleMenuMapping;
import com.sunsea.parkinghere.biz.model.Menu;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.MS;
import com.sunsea.parkinghere.biz.service.RS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.framework.web.WebUtils;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;


@Controller
@RequestMapping("/api/menu")
public class MRSjfdsnfasoifhweoif extends AbstractRestService {
	
	@Autowired
	private MS dfsdfdsfsd;
	
	@Autowired
	private US jdsiofoifneoio4ji2;
	
	@Autowired
	private RS uiojfewnpejpewojrp32;
	 
	@Auditable
	@RequestMapping(value = "/getTagMenu", method = RequestMethod.GET)
    @ResponseBody
	public Object fdsiofnewlfneoi(){
		try{
			User user = this.dsfsddfd3();
			if(!UserRoleMenuMapping.getUser_role().containsKey(user.getId())){
				user = jdsiofoifneoio4ji2.findById(user.getId());
				UserRoleMenuMapping.getUser_role().put(user.getId(), user.getRoles());
			}
			List<Role> roles = UserRoleMenuMapping.getUser_role().get(user.getId());
			if(roles==null||roles.isEmpty()){
				return WebUtils.succeedMap(new HashMap<String,Object>());
			}
			String roleNames = "";
			for(Role role : roles){
				if(role.getMenus()==null||role.getMenus().isEmpty()){
					role.setMenus(uiojfewnpejpewojrp32.fbi(role.getId()).getMenus());
				}
				roleNames += role.getName()+"|";
			}
			roleNames = roleNames.substring(0, roleNames.length()-1);
			
			Map<String,Object> role_menu = UserRoleMenuMapping.getRole_menus().get(roleNames);
			if(role_menu!=null&&!role_menu.isEmpty()){
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> topMenuList  = (List<Map<String, Object>>)role_menu.get("topMenu");
				@SuppressWarnings("unchecked")
				Map<String, List<Map<String, Object>>> smapLists = (Map<String, List<Map<String, Object>>>)role_menu.get("subMenu");
				if(topMenuList!=null&&!topMenuList.isEmpty()){
					if(smapLists!=null&&!smapLists.isEmpty()){
						return WebUtils.succeedMap(role_menu);
					}
				}
			}
			
			
			Map<String,List<Menu>> subMenuMap = new HashMap<String,List<Menu>>();
			
			List<Menu> topMenuList = new ArrayList<Menu>();
			
			if(topMenuList.isEmpty()){
				List<Menu> allMenuList = new ArrayList<Menu>();
				for(Role role : roles){
					List<Menu> menus = role.getMenus();
					if(menus!=null){
						allMenuList.addAll(menus);
						
						for(Menu menu : menus){
							List<Menu> subMenus = dfsdfdsfsd.fsm(menu.getId());
							if(subMenus!=null){
								allMenuList.addAll(subMenus);
							}
						}
					}
				}
				for(Menu menu : allMenuList){
					if(menu.getLevel()==1){
						topMenuList.add(menu);
					}else if(menu.getLevel()==2){
						List<Menu> subMenuList = subMenuMap.get(menu.getParentMenu().getId());
						if(subMenuList==null){
							subMenuList = new ArrayList<Menu>();
							subMenuMap.put(menu.getParentMenu().getId(), subMenuList);
						}
						boolean isExist = false;
						for(Menu subMenu: subMenuList){
							if(menu.getId().equals(subMenu.getId())){
								isExist = true;
							}
						}
						if(!isExist){
							subMenuList.add(menu);
						}
					}
				}
			}
			
			role_menu = this.nhfuigoebwoiep3232cs(topMenuList, subMenuMap);
			UserRoleMenuMapping.getRole_menus().put(roleNames, role_menu);
			return WebUtils.succeedMap(role_menu);
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/initMenu", method = RequestMethod.GET)
    @ResponseBody
	public Object fdsioi8r23fdx(){
		try{
			dfsdfdsfsd.im();
			return WebUtils.succeedMap();
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/cleanUserRoleMenu", method = RequestMethod.GET)
    @ResponseBody
	public Object ddfdsfdfdfsdiopew(){
		try{
			UserRoleMenuMapping.getRole_menus().clear();
			UserRoleMenuMapping.getUser_role().clear();
			return WebUtils.succeedMap();
		} catch(Exception e){
			e.printStackTrace();
			return WebUtils.failedMap(e.getMessage());
		}
	}
	
	private Map<String,Object> nhfuigoebwoiep3232cs(List<Menu> ertyuiogfds, Map<String,List<Menu>> qaznidhcyhd){
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
		Map<String, List<Map<String, Object>>> smapLists = new HashMap<String, List<Map<String, Object>>>();
		for(Menu topMenu : ertyuiogfds){
			Map<String, Object> tmap = new HashMap<String, Object>();
			tmap.put("id", topMenu.getId());
			tmap.put("name", topMenu.getName());
			tmap.put("url", topMenu.getUrl());
			tlist.add(tmap);
			
			List<Menu> subMenus = qaznidhcyhd.get(topMenu.getId());
			if(subMenus==null){
				continue;
			}
			List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
			smapLists.put(topMenu.getId(), slist);
			for(Menu subMenu : subMenus){
				Map<String, Object> smap = new HashMap<String, Object>();
				smap.put("id", subMenu.getId());
				smap.put("name", subMenu.getName());
				smap.put("url", subMenu.getUrl());
				slist.add(smap);
			}
		}
		map.put("topMenu", tlist);
		map.put("subMenu", smapLists);
		
		System.out.println(JSON.toJSONString(map));
		return map;
	}
	
	

}
