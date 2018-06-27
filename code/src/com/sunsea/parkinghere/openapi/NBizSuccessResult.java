package com.sunsea.parkinghere.openapi;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.BeanSerializer;

public class NBizSuccessResult {
	private int code;
	@JsonSerialize(using = BeanSerializer.class)
	private Object data;

	public NBizSuccessResult(Object data) {
		this.code = 200;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public Object getData() {
		return data == null ? "{}" : data;
	}
}
