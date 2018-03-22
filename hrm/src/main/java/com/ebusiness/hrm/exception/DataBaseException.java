package com.ebusiness.hrm.exception;

/**
 * 数据库操作异常
 * @author Lyk
 *
 */
public class DataBaseException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DataBaseException() {
		super();
	}

	public DataBaseException(String msg) {
		super(msg);
	}

	public DataBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DataBaseException(Throwable cause) {
		super(cause);
	}

}
