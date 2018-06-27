package com.sunsea.parkinghere.openapi.das;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.SwitchStatusRecord;
import com.sunsea.parkinghere.biz.model.Transducer;
import com.sunsea.parkinghere.biz.model.TransducerDataRecord;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.TS;
import com.sunsea.parkinghere.openapi.NBizBaseFaceService;
import com.sunsea.parkinghere.openapi.NBizExceptionResult;

@Controller
@RequestMapping("/api/das")
public class DAS extends NBizBaseFaceService {
	
	@Autowired
	private TS rwer323fdffv;
	@Autowired
	private SS fdsfdsfdsfd;

	
	@RequestMapping(value = "/transducer", method = RequestMethod.POST)
	@ResponseBody
	public Object fdsf123fdsds(@RequestBody TDP fdsfsdere){
		try{
			TransducerDataRecord tr = new TransducerDataRecord();
			
			Date date = new Date();
			tr.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
			tr.setDay(fdsfsdere.getTime().substring(0, 10));
			tr.setHour(fdsfsdere.getTime().substring(11, 13));
			tr.setSourceTime(fdsfsdere.getTime());
			Transducer transducer = rwer323fdffv.fbu(fdsfsdere.getId());
			if(transducer==null){
				System.out.println("uuid="+fdsfsdere.getId()+" 找不到对应的传感器");
				return new NBizExceptionResult(300, "uuid="+fdsfsdere.getId()+" 找不到对应的传感器");
			}
			tr.setTransducerId(transducer.getId());
			tr.setValue(fdsfsdere.getValue());
			tr.setDataType(fdsfsdere.getType());
			
			rwer323fdffv.ctdr(tr);
			
			
			rwer323fdffv.sv(transducer.getId(), fdsfsdere.getType(), fdsfsdere.getValue(), fdsfsdere.getTime());
			
			
			try{
				final String transducerId = transducer.getId();
				final Integer dataType = fdsfsdere.getType();
				final Float value = fdsfsdere.getValue();
				new Thread(new Runnable() {
					public void run() {
						rwer323fdffv.jadaa(transducerId, dataType, value);
					}
				}).start();;
			}catch(Exception e){}
			return toSuccessResult(null);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/switch", method = RequestMethod.POST)
	@ResponseBody
	public Object fdsfdsfd34fdsds(@RequestBody SDP dsfdiufhuio){
		try{
			
			
			assertNotBlank(dsfdiufhuio.getId(), 300, "缺少id参数");
			assertNotBlank(dsfdiufhuio.getStatus(), 300, "缺少status参数");
			assertNotBlank(dsfdiufhuio.getTime(), 300, "缺少time参数");
			
			Date date = new Date();
			
			
			String[] statuses = dsfdiufhuio.getStatus().split(" ");
			
			List<Switch> switchList = fdsfdsfdsfd.fbu(dsfdiufhuio.getId());
			if(switchList==null||switchList.isEmpty()){
				return toSuccessResult(null);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(Switch _switch : switchList){
				String status = statuses[_switch.getPosition()];
				SwitchStatusRecord ss = new SwitchStatusRecord();
				ss.setSwitchId(_switch.getId());
				ss.setCreateTime(sdf.format(date));
				ss.setDay(dsfdiufhuio.getTime().substring(0, 10));
				ss.setHour(dsfdiufhuio.getTime().substring(11, 13));
				ss.setSourceTime(dsfdiufhuio.getTime());
				ss.setStauts(Integer.valueOf(status));
				ss.setType(null);
				fdsfdsfdsfd.cssr(ss);
				
				
				fdsfdsfdsfd.st(_switch.getId(), ss.getStauts(), dsfdiufhuio.getTime());
			}
			
			return toSuccessResult(null);
		}catch(Exception e){
			e.printStackTrace();
			return new NBizExceptionResult(300, e.getMessage());
		}
	}
}
