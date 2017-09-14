package com.rong.common.exception;

public class ParamCheckException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamCheckException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParamCheckException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ParamCheckException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ParamCheckException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParamCheckException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	String param;

	public String getParam() {
		return param;
	}

	public ParamCheckException setParam(String param) {
		this.param = param;
		return this;
	}

}
