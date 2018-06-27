package com.sunsea.parkinghere.biz.service;

import java.util.List;

import com.sunsea.parkinghere.biz.model.TransducerMessage;
import com.sunsea.parkinghere.biz.model.UserMessageReadTime;

public interface MeSd {
	
	
	public TransducerMessage flnm(String a,Long b);
	
	
	public List<TransducerMessage> fm(String a, Integer b, Integer c);
	
	
	public void rm(String a);

	public UserMessageReadTime fumrtbui(String a);
	
	public void srt(Long a,String b,String c);
}
