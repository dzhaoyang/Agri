package com.sunsea.parkinghere.biz.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Switches")
public class Switch extends Identity {
	
	 
	private String uuid;
	 
	@Indexed
	@DBRef
	private GreenHouse greenHouse;
	 
	private String name;
	 
	private Integer type;
	private String typestr;
	 
	private Integer stauts;
	 
	private String installDate;
	 
	private Integer coordinateX;
	private Integer coordinateY;
	 
	private String createTime;
	 
	private String rWId;
	 
	private Integer position;
	 
	private String location;
	 
	private String lastUpdateTime;
	 
	private String lastOperatorId;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public GreenHouse getGreenHouse() {
		return greenHouse;
	}
	public void setGreenHouse(GreenHouse greenHouse) {
		this.greenHouse = greenHouse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypestr() {
		return typestr;
	}
	public void setTypestr(String typestr) {
		this.typestr = typestr;
	}
	public Integer getStauts() {
		return stauts;
	}
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public Integer getCoordinateX() {
		return coordinateX;
	}
	public void setCoordinateX(Integer coordinateX) {
		this.coordinateX = coordinateX;
	}
	public Integer getCoordinateY() {
		return coordinateY;
	}
	public void setCoordinateY(Integer coordinateY) {
		this.coordinateY = coordinateY;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getrWId() {
		return rWId;
	}
	public void setrWId(String rWId) {
		this.rWId = rWId;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLastOperatorId() {
		return lastOperatorId;
	}
	public void setLastOperatorId(String lastOperatorId) {
		this.lastOperatorId = lastOperatorId;
	}
}
