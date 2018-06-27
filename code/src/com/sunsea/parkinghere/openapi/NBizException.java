package com.sunsea.parkinghere.openapi;

import java.text.MessageFormat;

public class NBizException extends RuntimeException {
	private static final long serialVersionUID = -1591774287895238887L;
	private static final MessageFormat pattern = new MessageFormat(
			"错误码为:{0,number,####},消息:{1}");
	private static final String DEFAULT_MSG = "内部错误";
	private int code;
	private String bizMessage;

	public NBizException(int code, String bizMessage) {
		super(pattern.format(new Object[] { code, bizMessage }));
		this.code = code;
		this.bizMessage = bizMessage;
	}

	public NBizException(int code, Throwable t) {
		super(pattern.format(new Object[] { code, DEFAULT_MSG }), t);
		this.code = code;
		this.bizMessage = DEFAULT_MSG;
	}

	public NBizException(int code, String bizMessage, Throwable t) {
		super(pattern.format(new Object[] { code, bizMessage }), t);
		this.code = code;
		this.bizMessage = bizMessage;
	}

	public int getCode() {
		return code;
	}

	public String getBizMessage() {
		return bizMessage;
	}
}
