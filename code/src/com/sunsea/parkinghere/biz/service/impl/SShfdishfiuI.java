package com.sunsea.parkinghere.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mongodb.WriteResult;
import com.sunsea.parkinghere.biz.model.GreenHouse;
import com.sunsea.parkinghere.biz.model.Switch;
import com.sunsea.parkinghere.biz.model.SwitchOperateRecord;
import com.sunsea.parkinghere.biz.model.SwitchStatusRecord;
import com.sunsea.parkinghere.biz.model.SwitchType;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.SS;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.module.audit.openapi.AbstractQueryParameter;
import com.sunsea.parkinghere.webservice.client.das.SwitchControler;
import com.sunsea.parkinghere.webservice.client.das.SwitchControlerSoap;
/**
 * 开发服务接口实现类
 * @author ylr
 *
 */
@Service
public class SShfdishfiuI implements SS {

	@Autowired
	private MongoTemplate fsdf3;
	@Autowired
    US er3;

	@Override
	public Switch fbi2(String fdsf6) {
		return fsdf3.findById(fdsf6, Switch.class);
	}
	
	@Override
	public List<Switch> fbu(String fdgr) {
		Query query = new Query();
		query.addCriteria(Criteria.where("uuid").is(fdgr));
		return fsdf3.find(query, Switch.class);
	}

	@Override
	public Page<Switch> fbp(String fds, String ty, String bregrt, Integer ui7, Integer rf5, Integer jk8j) {
		AbstractQueryParameter parameter = new AbstractQueryParameter();
		parameter.setLimit(jk8j);
		parameter.setStart(rf5);
		rf5 = AbstractQueryParameter.getStart(parameter);
        jk8j = AbstractQueryParameter.getLimit(parameter);
        PageRequest pageable = new PageRequest(rf5, jk8j);
        
        Query query = new Query();
        if(StringUtils.isNotBlank(fds)){
        	query.addCriteria(Criteria.where("uuid").is(fds));
        }
        if(StringUtils.isNotBlank(ty)){
        	query.addCriteria(Criteria.where("name").is(ty));
        }
        if(StringUtils.isNotBlank(bregrt)){
        	GreenHouse greenHouse = new GreenHouse();
        	greenHouse.setId(bregrt);
        	query.addCriteria(Criteria.where("greenHouse").is(greenHouse));
        }
        if(ui7!=null){
        	query.addCriteria(Criteria.where("type").is(ui7));
        }
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        query.with(pageable);
        
        long count = fsdf3.count(query, Switch.class);
        List<Switch> list = fsdf3.find(query, Switch.class);
		return new PageImpl<Switch>(list, pageable, count);
	}

	@Override
	public List<Switch> fbl() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		return fsdf3.find(query, Switch.class);
	}
	
	@Override
	public List<Switch> fbl(String fdfvdfv4) {
		Query query = new Query();
		GreenHouse greenHouse = new GreenHouse();
		greenHouse.setId(fdfvdfv4);
		query.addCriteria(Criteria.where("greenHouse").is(greenHouse));
		query.with(new Sort(Sort.Direction.DESC,"createTime"));
		return fsdf3.find(query, Switch.class);
	}

	@Override
	public void fbi(String fdsfew) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(fdsfew));
		fsdf3.remove(query, Switch.class);
	}
	
	@Override
	public void dbgh(String dfdeg) {
		Query query = new Query();
		GreenHouse greenHouse = new GreenHouse();
    	greenHouse.setId(dfdeg);
    	query.addCriteria(Criteria.where("greenHouse").is(greenHouse));
		fsdf3.remove(query, Switch.class);
	}

	@Override
	public void srewrewrew(Switch nyu76) {
		if(StringUtils.isBlank(nyu76.getId())){
			nyu76.setStauts(0);
			fsdf3.save(nyu76);
			return;
		}
		Switch old = this.fbi2(nyu76.getId());
		old.setCoordinateX(nyu76.getCoordinateX());
		old.setCoordinateY(nyu76.getCoordinateY());
		old.setGreenHouse(nyu76.getGreenHouse());
		old.setInstallDate(nyu76.getInstallDate());
		old.setName(nyu76.getName());
		old.setUuid(nyu76.getUuid());
		old.setrWId(nyu76.getrWId());
		old.setPosition(nyu76.getPosition());
		old.setLocation(nyu76.getLocation());
		fsdf3.save(old);
	}

	@Override
	public void st(String fdfds, int f7km, String xfe5j) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(fdfds));
		fsdf3.updateFirst(query, Update.update("stauts", f7km).set("lastUpdateTime", xfe5j), Switch.class);
	}

	@Override
	public String ut(final String uy87, User ki8, final int fd55d) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(uy87));
		if(fd55d==1){//打开
			query.addCriteria(Criteria.where("stauts").is(0));
		}else if(fd55d==0){//关闭
			query.addCriteria(Criteria.where("stauts").is(1));
		}
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Update update = Update.update("stauts", fd55d==1?2:3);
		update.set("lastOperatorId", ki8==null?null:ki8.getId());
		update.set("lastUpdateTime", sdf.format(date));
		WriteResult wr = fsdf3.updateFirst(query, update, Switch.class);
		
		if(wr.getN()>0){
			// 创建用户操作记录
			this.csor(uy87, ki8==null?null:ki8.getId(), fd55d, sdf.format(date));
			// 调用das接口触发物理开关
			try{
				new Thread(new Runnable() {
					public void run() {
						try{
							boolean isOk = cdos(uy87, fd55d);
							if(isOk){
								/*Thread.sleep(1000*30);
								callDasGetSwitchStatus(switchId);//暂时没有查询开关状态的api，所以操作成功后使用下面的代码*/
								Query query = new Query();
								query.addCriteria(Criteria.where("id").is(uy87));
								fsdf3.updateFirst(query, Update.update("stauts", fd55d), Switch.class);
							}else{//如果下发失败，就将状态改回
								Query query = new Query();
								query.addCriteria(Criteria.where("id").is(uy87));
								fsdf3.updateFirst(query, Update.update("stauts", (fd55d==1?0:1)), Switch.class);
							}
						}catch(Exception e){//如果下发失败，就将状态改回
							e.printStackTrace();
							Query query = new Query();
							query.addCriteria(Criteria.where("id").is(uy87));
							fsdf3.updateFirst(query, Update.update("stauts", (fd55d==1?0:1)), Switch.class);
						}
					}
				}).start();
			}catch(Exception e){}
			return sdf.format(date);
		}
		return "";
	}

	@Override
	public void elc(Map<String, Integer> frvrm) {
		if(frvrm==null||frvrm.isEmpty()){
			return;
		}
		for(Entry<String, Integer> entry : frvrm.entrySet()){
			try{
				final String switchId = entry.getKey();
				final Integer status = entry.getValue();
				new Thread(new Runnable() {
					public void run() {
						try{
							Query query = new Query();
							query.addCriteria(Criteria.where("id").is(switchId));
							fsdf3.updateFirst(query, Update.update("stauts", (status==1?2:3)), Switch.class);
							boolean isOk = cdos(switchId, status);
							if(isOk){
								/*Thread.sleep(1000*30);
								callDasGetSwitchStatus(switchId);//暂时没有查询开关状态的api，所以操作成功后使用下面的代码*/
								query = new Query();
								query.addCriteria(Criteria.where("id").is(switchId));
								fsdf3.updateFirst(query, Update.update("stauts", status), Switch.class);
							}else{//如果下发失败，就将状态改回
								query = new Query();
								query.addCriteria(Criteria.where("id").is(switchId));
								fsdf3.updateFirst(query, Update.update("stauts", (status==1?0:1)), Switch.class);
							}
						}catch(Exception e){
							Query query = new Query();
							query.addCriteria(Criteria.where("id").is(switchId));
							fsdf3.updateFirst(query, Update.update("stauts", (status==1?0:1)), Switch.class);
						}
					}
				}).start();
			}catch(Exception e){}
		}
	}

	@Override
	public void cssr(SwitchStatusRecord fdsfdde35) {
		fsdf3.save(fdsfdde35);
	}
	
	private void csor(String gfd, String gt, int bn, String tergtrhbs){
		SwitchOperateRecord sr = new SwitchOperateRecord();
		sr.setSwitchId(gfd);
		sr.setOperaterId(gt);
		sr.setStauts(bn);
		sr.setOperateTime(tergtrhbs);
		fsdf3.save(sr);
	}

	@Override
	public Page<Map<String, Object>> fwor(String fds, Integer fsfdfsfdsfdsf,Integer gfdvtr34azxk) {
		AbstractQueryParameter parameter = new AbstractQueryParameter();
		parameter.setLimit(gfdvtr34azxk);
		parameter.setStart(fsfdfsfdsfdsf);
		fsfdfsfdsfdsf = AbstractQueryParameter.getStart(parameter);
        gfdvtr34azxk = AbstractQueryParameter.getLimit(parameter);
        PageRequest pageable = new PageRequest(fsfdfsfdsfdsf, gfdvtr34azxk);
        
		Query query = new Query();
		query.addCriteria(Criteria.where("switchId").is(fds));
		query.with(new Sort(Sort.Direction.DESC,"operateTime"));
		
		query.with(pageable);
		
		long count = fsdf3.count(query, SwitchOperateRecord.class);
        List<SwitchOperateRecord> list = fsdf3.find(query, SwitchOperateRecord.class);
        
		List<Map<String, Object>> operateRecordList = new ArrayList<Map<String, Object>>();
		for(SwitchOperateRecord sr : list){
			Map<String, Object> sr_map = new LinkedHashMap<String, Object>();
			Switch _switch = fsdf3.findById(sr.getSwitchId(), Switch.class);
			sr_map.put("operateTime", sr.getOperateTime());
			sr_map.put("stauts", SwitchType.getStatusName(_switch.getType(), sr.getStauts())/*sr.getStauts()==1?"打开":"关闭"*/);
			sr_map.put("operatorName", "");
			if(StringUtils.isNotBlank(sr.getOperaterId())){
				User operator = er3.findById(sr.getOperaterId());
				if(operator!=null){
					sr_map.put("operatorName", operator.getName());
				}
			}
			operateRecordList.add(sr_map);
		}
		System.out.println(operateRecordList);
		return new PageImpl<Map<String, Object>>(operateRecordList, pageable, count);
	}
	
	@Override
	public boolean cdos(String sdsada, int fdfgdf) {
		SwitchControler switchControler = new SwitchControler();
		SwitchControlerSoap switchControlerSoap = switchControler.getSwitchControlerSoap();
		Switch _switch = this.fbi2(sdsada);
		String switchModeSequence = bsms(_switch.getPosition(), fdfgdf);
		String result = switchControlerSoap.setSwitch(_switch.getrWId(), _switch.getUuid(), switchModeSequence);
		System.out.println("callDasOperateSwitch result === "+result);
		Map<String, Object> map = JSON.parseObject(result, Map.class);
		if(Integer.parseInt(map.get("result_code").toString())!=1){
			return false;
		}
		return true;
	}
	
	private void cdgss(String verg){
		Switch _switch = this.fbi2(verg);
		List<Switch> switchList = fbu(_switch.getUuid());
		
		SwitchControler switchControler = new SwitchControler();
		SwitchControlerSoap switchControlerSoap = switchControler.getSwitchControlerSoap();
		String result = switchControlerSoap.readSwitchStatus(_switch.getrWId(), _switch.getUuid());
		System.out.println("callDasGetSwitchStatus result === "+result);
		Map<String, Object> map = JSON.parseObject(result, Map.class);
		if(Integer.parseInt(map.get("result_code").toString())==1){
			String resultMsg = map.get("result_msg").toString();
			String[] statuses = resultMsg.split(" ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			for(Switch __switch : switchList){
				String status = statuses[_switch.getPosition()];
				SwitchStatusRecord ss = new SwitchStatusRecord();
				ss.setSwitchId(__switch.getId());
				ss.setCreateTime(time);
				ss.setDay(time.substring(0, 10));
				ss.setHour(time.substring(11, 13));
				ss.setSourceTime(time);
				ss.setStauts(Integer.valueOf(status));
				ss.setType(null);
				cssr(ss);
				
				// 修改开关当前状态
				st(_switch.getId(), ss.getStauts(), time);
			}
		}
	}
	
	/**
	 * 拼凑开关命令
	 * @param fds	在中继器中的位置
	 * @param frc	开关状态 1：打开，0：关闭
	 * @return
	 */
	private String bsms(int fds, int frc){
		String switchModeSequence = "";
		for(int i=1;i<=12;i++){
			if(i!=fds){
				switchModeSequence+="FE ";
			}else{
				if(frc==1){//打开
					switchModeSequence+="01 ";
				}else if(frc==0){//关闭
					switchModeSequence+="00 ";
				}
			}
		}
		if(switchModeSequence.length()!=0){
			switchModeSequence = switchModeSequence.substring(0, switchModeSequence.length()-1);
		}
		return switchModeSequence;
	}
	
}
