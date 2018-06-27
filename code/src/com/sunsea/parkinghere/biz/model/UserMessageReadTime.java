package com.sunsea.parkinghere.biz.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

 
@Document(collection = "UserMessageReadTimes")
public class UserMessageReadTime {

	@Indexed
	private String userId;
	
	private Long readLongTime;
	
	private String readStringTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getReadLongTime() {
		return readLongTime;
	}

	public void setReadLongTime(Long readLongTime) {
		this.readLongTime = readLongTime;
	}

	public String getReadStringTime() {
		return readStringTime;
	}

	public void setReadStringTime(String readStringTime) {
		this.readStringTime = readStringTime;
	}
}
