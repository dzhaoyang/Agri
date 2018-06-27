package com.sunsea.parkinghere.exception;

import java.text.MessageFormat;

public class InternalException extends RuntimeException {
	private static final long serialVersionUID = -1551084084652667088L;
	private static final String DEFAULT_MESS = "程序内部错误";
	private static final String ERROR_PREFIX = ",错误消息:";

	public InternalException(String message) {
		super(message);
	}

	public InternalException(String message, Object... params) {
		super(MessageFormat.format(message, params));
	}

	public InternalException(Throwable cause) {
		super(DEFAULT_MESS, cause);
	}

	public InternalException(String message, Throwable cause) {
		super(message + ERROR_PREFIX, cause);
	}

	public InternalException(String message, Throwable cause, Object... params) {
		super(MessageFormat.format(message, params) + ERROR_PREFIX, cause);
	}
}
