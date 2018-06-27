package com.sunsea.parkinghere.biz.model;

import java.util.Map;


public class RegistrationField {
	 
	private String label;
	 
	private String subLabel;
	 
	private String type;
	 
	private int isRequired = 0;
	 
	private String[] timeRanges;
	 
	private int isTextarea = 1;
	 
	private String picture;
	 
	private String[] textOptions;
	
	private Map<String,Object>[] pictureOptions;
	 
	private int multiple;
	 
	private int multiColumns;
	 
	private String minSelect;
	 
	private String maxSelect;
	 
	private String value;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getSubLabel() {
		return subLabel;
	}
	public void setSubLabel(String subLabel) {
		this.subLabel = subLabel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	public String[] getTimeRanges() {
		return timeRanges;
	}
	public void setTimeRanges(String[] timeRanges) {
		this.timeRanges = timeRanges;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String[] getTextOptions() {
		return textOptions;
	}
	public void setTextOptions(String[] textOptions) {
		this.textOptions = textOptions;
	}
	public Map<String, Object>[] getPictureOptions() {
		return pictureOptions;
	}
	public void setPictureOptions(Map<String, Object>[] pictureOptions) {
		this.pictureOptions = pictureOptions;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public int getMultiColumns() {
		return multiColumns;
	}
	public void setMultiColumns(int multiColumns) {
		this.multiColumns = multiColumns;
	}
	public int getIsTextarea() {
		return isTextarea;
	}
	public void setIsTextarea(int isTextarea) {
		this.isTextarea = isTextarea;
	}
	public String getMinSelect() {
		return minSelect;
	}
	public void setMinSelect(String minSelect) {
		this.minSelect = minSelect;
	}
	public String getMaxSelect() {
		return maxSelect;
	}
	public void setMaxSelect(String maxSelect) {
		this.maxSelect = maxSelect;
	}
}
