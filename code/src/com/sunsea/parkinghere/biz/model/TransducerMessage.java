package com.sunsea.parkinghere.biz.model;

import org.springframework.data.mongodb.core.mapping.Document;
 
@Document(collection = "TransducerMessages")
public class TransducerMessage extends Identity {

	 
	private String title;
	 
	private String content;
	 
	private Integer status;
	 
	private String transducerId;
	 
	private Integer transducerType;
	 
	private Integer transducerStatus;
	 
	private String createTime;
	 
	private Long time;
	 
	private String userId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTransducerId() {
		return transducerId;
	}

	public void setTransducerId(String transducerId) {
		this.transducerId = transducerId;
	}

	public Integer getTransducerType() {
		return transducerType;
	}

	public void setTransducerType(Integer transducerType) {
		this.transducerType = transducerType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTransducerStatus() {
		return transducerStatus;
	}

	public void setTransducerStatus(Integer transducerStatus) {
		this.transducerStatus = transducerStatus;
	}
}
