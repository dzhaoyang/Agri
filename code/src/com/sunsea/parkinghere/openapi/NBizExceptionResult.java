package com.sunsea.parkinghere.openapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NBizExceptionResult {
	private int code;
	private String message;
	private String detailMessage;

	public NBizExceptionResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public NBizExceptionResult(int code, String message,
			String detailMessage) {
		this.code = code;
		this.message = message;
		this.detailMessage = detailMessage;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDetailMessage() {
		return detailMessage;
	}
}
