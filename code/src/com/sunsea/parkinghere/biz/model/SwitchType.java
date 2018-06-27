package com.sunsea.parkinghere.biz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SwitchType {
	 
	public static final Integer penguantou = 1;
	 
	public static final Integer juanlian = 2;
	 
	public static final Integer dafengji = 3;
	 
	public static final Integer huanliufengshan = 4;
	 
	public static final Integer neizheyang_zhankai = 5;
	 
	public static final Integer neizheyang_shoulong = 6;
	 
	public static final Integer wiazheyang_zhankai = 7;
	 
	public static final Integer waizheyang_sholong = 8;
	 
	public static final Integer dingjuan_zhankai = 9;
	 
	public static final Integer dingjuan_shoulong = 10;
	 
	public static final Integer pengwu = 11;
	
	 
	public static final Integer status_open = 1;
	 
	public static final Integer status_opening = 2;
	 
	public static final Integer close = 0;
	 
	public static final Integer closing = 3;
	 
	public static final Integer furl = 1;
	 
	public static final Integer furling = 2;
	 
	public static final Integer stop_open = 0;
	 
	public static final Integer stoping_open = 3;
	 
	public static final Integer stop_furl = 0;
	 
	public static final Integer stoping_furl = 3;
	
	
	public static String getName(Integer type){
		String name = "";
		if(type.intValue()==juanlian.intValue()){
			name = "卷帘";
		}else if(type.intValue()==penguantou.intValue()){
			name = "喷灌头";
		}else if(type.intValue()==dafengji.intValue()){
			name = "大风机";
		}else if(type.intValue()==huanliufengshan.intValue()){
			name = "环流风扇";
		}else if(type.intValue()==neizheyang_zhankai.intValue()){
			name = "内遮阳展开";
		}else if(type.intValue()==neizheyang_shoulong.intValue()){
			name = "内遮阳收拢";
		}else if(type.intValue()==wiazheyang_zhankai.intValue()){
			name = "外遮阳展开";
		}else if(type.intValue()==waizheyang_sholong.intValue()){
			name = "外遮阳收拢";
		}else if(type.intValue()==dingjuan_zhankai.intValue()){
			name = "顶卷展开";
		}else if(type.intValue()==dingjuan_shoulong.intValue()){
			name = "顶卷收拢";
		}else if(type.intValue()==pengwu.intValue()){
			name = "卷帘";
		}
		return name;
	}
	
	
	public static List<Map<String, Object>> all(){
		List<Map<String, Object>> list = new ArrayList<>();
		for(int i=1;i<12;i++){
			Map<String, Object> map = new HashMap<>();
			map.put("id", Integer.valueOf(i));
			map.put("name", getName(Integer.valueOf(i)));
			list.add(map);
		}
		return list;
	}
	
	
	public static String getStatusName(Integer type,Integer status){
		String statusName = "";
		if(type.intValue()==juanlian.intValue()){
			if(status.intValue()==1){
				statusName = "打开";
			}else if(status.intValue()==2){
				statusName = "打开中";
			}else if(status.intValue()==0){
				statusName = "关闭";
			}else if(status.intValue()==3){
				statusName = "关闭中";
			}
		}else if(type.intValue()==penguantou.intValue()){
			if(status.intValue()==1){
				statusName = "打开";
			}else if(status.intValue()==2){
				statusName = "打开中";
			}else if(status.intValue()==0){
				statusName = "关闭";
			}else if(status.intValue()==3){
				statusName = "关闭中";
			}
		}else if(type.intValue()==dafengji.intValue()){
			if(status.intValue()==1){
				statusName = "打开";
			}else if(status.intValue()==2){
				statusName = "打开中";
			}else if(status.intValue()==0){
				statusName = "关闭";
			}else if(status.intValue()==3){
				statusName = "关闭中";
			}
		}else if(type.intValue()==huanliufengshan.intValue()){
			if(status.intValue()==1){
				statusName = "打开";
			}else if(status.intValue()==2){
				statusName = "打开中";
			}else if(status.intValue()==0){
				statusName = "关闭";
			}else if(status.intValue()==3){
				statusName = "关闭中";
			}
		}else if(type.intValue()==neizheyang_zhankai.intValue()){
			if(status.intValue()==1){
				statusName = "展开";
			}else if(status.intValue()==2){
				statusName = "展开中";
			}else if(status.intValue()==0){
				statusName = "停止展开";
			}else if(status.intValue()==3){
				statusName = "停止展开中";
			}
		}else if(type.intValue()==neizheyang_shoulong.intValue()){
			if(status.intValue()==1){
				statusName = "收拢";
			}else if(status.intValue()==2){
				statusName = "收拢中";
			}else if(status.intValue()==0){
				statusName = "停止收拢";
			}else if(status.intValue()==3){
				statusName = "停止收拢中";
			}
		}else if(type.intValue()==wiazheyang_zhankai.intValue()){
			if(status.intValue()==1){
				statusName = "展开";
			}else if(status.intValue()==2){
				statusName = "展开中";
			}else if(status.intValue()==0){
				statusName = "停止展开";
			}else if(status.intValue()==3){
				statusName = "停止展开中";
			}
		}else if(type.intValue()==waizheyang_sholong.intValue()){
			if(status.intValue()==1){
				statusName = "收拢";
			}else if(status.intValue()==2){
				statusName = "收拢中";
			}else if(status.intValue()==0){
				statusName = "停止收拢";
			}else if(status.intValue()==3){
				statusName = "停止收拢中";
			}
		}else if(type.intValue()==dingjuan_zhankai.intValue()){
			if(status.intValue()==1){
				statusName = "展开";
			}else if(status.intValue()==2){
				statusName = "展开中";
			}else if(status.intValue()==0){
				statusName = "停止展开";
			}else if(status.intValue()==3){
				statusName = "停止展开中";
			}
		}else if(type.intValue()==dingjuan_shoulong.intValue()){
			if(status.intValue()==1){
				statusName = "收拢";
			}else if(status.intValue()==2){
				statusName = "收拢中";
			}else if(status.intValue()==0){
				statusName = "停止收拢";
			}else if(status.intValue()==3){
				statusName = "停止收拢中";
			}
		}else if(type.intValue()==pengwu.intValue()){
			if(status.intValue()==1){
				statusName = "打开";
			}else if(status.intValue()==2){
				statusName = "打开中";
			}else if(status.intValue()==0){
				statusName = "关闭";
			}else if(status.intValue()==3){
				statusName = "关闭中";
			}
		}
		return statusName;
	}
	
}
