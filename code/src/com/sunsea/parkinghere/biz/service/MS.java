package com.sunsea.parkinghere.biz.service;

import java.util.List;

import com.sunsea.parkinghere.biz.model.Menu;


public interface MS {

	
	public void cm(Menu a);
	
	
	public List<Menu> fsm(String a);
	
	
	public List<Menu> fsm();
	
	
	public List<Menu> ftm();
	
	
	public void dbi(String a);
	
	
	public Menu fbn(String a);
	
	public void im();
	
	
	public void un(String a,String b);
}
