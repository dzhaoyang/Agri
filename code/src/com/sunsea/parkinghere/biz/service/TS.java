package com.sunsea.parkinghere.biz.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sunsea.parkinghere.biz.dto.TfjdisfjsdiDto;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataRecord;


public interface TS {

	
	public Transducer fbi(String id);
	
	
	public Transducer fbu(String a);
	
	
	public Page<Transducer> fbp(String a,String b,String c,Integer d, Integer e);
	
	
	public List<Transducer> fbl();
	
	
	public List<Transducer> fbl(String a);
	
	
	public List<Transducer> fbl(String a,Integer b);
	
	
	public void dbi(String a);
	
	
	public void dbgh(String a);
	
	
	public void serevf312(Transducer a);
	
	
	public void sv(String a,Integer b,Float c,String d);
	
	
	public List<TfjdisfjsdiDto> f24hrbd(String a, Integer b, String c);
	
	
	public List<TfjdisfjsdiDto> f24hrbd(String a, String b);
	
	
	public List<TfjdisfjsdiDto> fdrbdt(String a, String b, String c, String d, String e);
	
	
	public List<TfjdisfjsdiDto> fdrbat(String a, String b, String c);
	
	
	public void ctdr(TransducerDataRecord a);
	
	
	public void jadaa(String a, Integer b, Float c);
	
	
	public String fmbdt(Integer a);
}
