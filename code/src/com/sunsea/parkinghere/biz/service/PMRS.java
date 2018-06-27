package com.sunsea.parkinghere.biz.service;

import java.util.Map;

public interface PMRS {
	
	public static final String cn = "PasswordModifyRecords";

	
	public Map<String,Object> fmc(String a,String b,String c);
	
	
	public void an(String a);
	
	
	public void cr(String a,String b,String c);
}
