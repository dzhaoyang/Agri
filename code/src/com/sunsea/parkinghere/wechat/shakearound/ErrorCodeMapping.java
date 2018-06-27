package com.sunsea.parkinghere.wechat.shakearound;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeMapping {

	private static Map<String,String> map;
	
	static{
		map = new HashMap<String,String>();
		map.put("-1", "系统繁忙，此时请开发者稍候再试");
		map.put("0", "请求成功");
	}
	
	public static String getCodeSting(String code){
		return map.get(code);
	}
}
