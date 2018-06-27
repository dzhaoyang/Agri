package com.sunsea.parkinghere.biz.model;
 
public class TransducerDataType{
	
	 
	private Integer dataType;
	 
	private String dataTypeName;
	 
	private Float upperLimit;
	 
	private Float lowerLimit;
	 
	private String measure;
	
	private Integer isAuto = 0;
	 
	private String upperLimitCommand;
	 
	private String lowerLimitCommand;
	 
	private Float value;
	 
	private String lastUpdateTime;
	
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public Float getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(Float upperLimit) {
		this.upperLimit = upperLimit;
	}
	public Float getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(Float lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getUpperLimitCommand() {
		return upperLimitCommand;
	}
	public void setUpperLimitCommand(String upperLimitCommand) {
		this.upperLimitCommand = upperLimitCommand;
	}
	public String getLowerLimitCommand() {
		return lowerLimitCommand;
	}
	public void setLowerLimitCommand(String lowerLimitCommand) {
		this.lowerLimitCommand = lowerLimitCommand;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}
	
	/**
	 * 1：二氧化碳；2：PM2.5；3：土壤湿度；4：空气温度；5：光照度；6：空气湿度
	 * @param dataType
	 * @return
	 */
	public static String getDataTypeName(int dataType){
		switch (dataType) {
		case 1:
			return "二氧化碳";
		case 2:
			return "PM2.5";
		case 3:
			return "土壤湿度";
		case 4:
			return "空气温度";
		case 5:
			return "光照度";
		case 6:
			return "空气湿度";
		default:
			return "";
		}
	}
	
	public static Integer[] getDataTypes(){
		return new Integer[]{1,2,3,4,5,6};
	}
}
