package com.sunsea.parkinghere.exception;


public class DataObjProxyException extends InternalException {
	private static final long serialVersionUID = -336722580214000000L;

	/**
	 * @param message
	 * @param params
	 */
	public DataObjProxyException(String message, Object... params) {
		super(message, params);
	}

	/**
	 * @param message
	 * @param cause
	 * @param params
	 */
	public DataObjProxyException(String message, Throwable cause,
			Object... params) {
		super(message, cause, params);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataObjProxyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DataObjProxyException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DataObjProxyException(Throwable cause) {
		super(cause);
	}
}
