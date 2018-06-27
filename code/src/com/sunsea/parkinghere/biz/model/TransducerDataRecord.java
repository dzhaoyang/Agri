package com.sunsea.parkinghere.biz.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TransducerDataRecords")
public class TransducerDataRecord extends Identity {
	
	private Integer dataType;
	
	private String transducerId;
	
	private Float value;
	
	private String day;
	 
	private String hour;
	 
	private String createTime;
	
	 
	private String sourceTime;
	
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public String getTransducerId() {
		return transducerId;
	}
	public void setTransducerId(String transducerId) {
		this.transducerId = transducerId;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
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
