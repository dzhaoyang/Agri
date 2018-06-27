package com.sunsea.parkinghere.biz.model;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "GreenHouses")
public class GreenHouse extends Identity {
	
	
	private String name;
	
	private String mapPhotoFileId;
	
    private String mapPhotoFileName;
	
    private String mapOriginalFileName;
	
	private Integer mapWidth;
	
	private Integer mapHeight;
	
	private Integer varsion;
	
	private String mapUpdateTime;
	
	private String createTime;
	
	private String menuId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMapPhotoFileId() {
		return mapPhotoFileId;
	}
	public void setMapPhotoFileId(String mapPhotoFileId) {
		this.mapPhotoFileId = mapPhotoFileId;
	}
	public String getMapOriginalFileName() {
		return mapOriginalFileName;
	}
	public void setMapOriginalFileName(String mapOriginalFileName) {
		this.mapOriginalFileName = mapOriginalFileName;
	}
	public Integer getMapWidth() {
		return mapWidth;
	}
	public void setMapWidth(Integer mapWidth) {
		this.mapWidth = mapWidth;
	}
	public Integer getMapHeight() {
		return mapHeight;
	}
	public void setMapHeight(Integer mapHeight) {
		this.mapHeight = mapHeight;
	}
	public Integer getVarsion() {
		return varsion;
	}
	public void setVarsion(Integer varsion) {
		this.varsion = varsion;
	}
	public String getMapUpdateTime() {
		return mapUpdateTime;
	}
	public void setMapUpdateTime(String mapUpdateTime) {
		this.mapUpdateTime = mapUpdateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMapPhotoFileName() {
		return mapPhotoFileName;
	}
	public void setMapPhotoFileName(String mapPhotoFileName) {
		this.mapPhotoFileName = mapPhotoFileName;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
