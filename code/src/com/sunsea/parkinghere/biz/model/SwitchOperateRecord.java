package com.sunsea.parkinghere.biz.model;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "SwitchOperateRecords")
public class SwitchOperateRecord extends Identity {
	
	 
	private String switchId;
	 
	private Integer stauts;
	 
	private String operaterId;
	 
	private String operateTime;
	
	public Integer getStauts() {
		return stauts;
	}
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getSwitchId() {
		return switchId;
	}
	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}
	public String getOperaterId() {
		return operaterId;
	}
	public void setOperaterId(String operaterId) {
		this.operaterId = operaterId;
	}
}
