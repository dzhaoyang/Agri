package com.sunsea.parkinghere.biz.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunsea.parkinghere.biz.model.Role;
/**
 * 用户、角色、菜单映射关系
 * @author ylr
 *
 */
public class UserRoleMenuMapping {

	/**角色名称或者角色名称组合对应的顶级菜单树*/
	private static Map<String,Map<String,Object>> role_menus = new HashMap<String,Map<String,Object>>();
	/**用户id与角色映射*/
	private static Map<String,List<Role>> user_role = new HashMap<String,List<Role>>();
	
	/**角色名称或者角色名称组合对应的顶级菜单树*/
	public static Map<String, Map<String, Object>> getRole_menus() {
		return role_menus;
	}
	
	/**角色名称或者角色名称组合对应的顶级菜单树*/
	public static void setRole_menus(Map<String, Map<String, Object>> role_menus) {
		UserRoleMenuMapping.role_menus = role_menus;
	}
	
	/**用户与角色映射*/
	public static Map<String, List<Role>> getUser_role() {
		return user_role;
	}
	
	/**用户与角色映射*/
	public static void setUser_role(Map<String, List<Role>> user_role) {
		UserRoleMenuMapping.user_role = user_role;
	}
	
	public static void deleteByUser(String userId){
		user_role.remove(userId);
	}
	
	public static void deleteByRole(){
		role_menus.clear();
	}
	
	public static void clearAll(){
		role_menus.clear();
		user_role.clear();
	}
}
