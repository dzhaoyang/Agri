package com.sunsea.parkinghere.biz.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SwitchStatusRecords")
public class SwitchStatusRecord extends Identity {

	 
	private Integer type;
	
	private String switchId;
	
	 
	private Integer stauts;
	 
	private String day;
	 
	private String hour;
	 
	private String createTime;
	

	 
	private String sourceTime;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSwitchId() {
		return switchId;
	}
	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}
	public Integer getStauts() {
		return stauts;
	}
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSourceTime() {
		return sourceTime;
	}
	public void setSourceTime(String sourceTime) {
		this.sourceTime = sourceTime;
	}
}
