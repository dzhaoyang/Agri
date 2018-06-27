package com.sunsea.parkinghere.openapi.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.TransducerMessage;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.model.UserMessageReadTime;
import com.sunsea.parkinghere.biz.service.MeSd;
import com.sunsea.parkinghere.openapi.NBizBaseFaceService;
import com.sunsea.parkinghere.openapi.NBizExceptionResult;


@Controller
@RequestMapping(value = "/api/app/message")
public class MAAShfdusifow extends NBizBaseFaceService {
	
	@Autowired
	private MeSd fdsfdfdsf;
	
	
	@RequestMapping(value = "/new/newest", method = RequestMethod.GET)
	@ResponseBody
	public Object dsfdsfdf432fds(){
		try{
			User user = null;
			try{
				user = this.getCurrentPrincipal();
			}catch(Exception e){
				return new NBizExceptionResult(1001001, "请登录！");
			}
			
			if(user==null){
				return new NBizExceptionResult(1001001, "请登录！");
			}
			
			UserMessageReadTime rt = fdsfdfdsf.fumrtbui(user.getId());
			TransducerMessage tm = fdsfdfdsf.flnm(user.getId(), rt==null?null:rt.getReadLongTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			fdsfdfdsf.srt(date.getTime(), sdf.format(date), user.getId());
			return toSuccessResult(tm);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/list/{pageSize}/{pageCount}", method = RequestMethod.GET)
	@ResponseBody
	public Object fdsfdfdfds(@PathVariable Integer pageSize, @PathVariable Integer pageCount){
		try{
			User user = null;
			try{
				user = this.getCurrentPrincipal();
			}catch(Exception e){
				return new NBizExceptionResult(1001001, "请登录！");
			}
			
			if(user==null){
				return new NBizExceptionResult(1001001, "请登录！");
			}
			List<TransducerMessage> list = fdsfdfdsf.fm(user.getId(), pageSize, pageCount);
			return toSuccessResult(list);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object fdsfdsf43fd(@PathVariable String id){
		try{
			fdsfdfdsf.rm(id);
			return toSuccessResult(null);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
}
