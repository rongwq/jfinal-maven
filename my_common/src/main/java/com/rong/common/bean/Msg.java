package com.rong.common.bean;

import java.io.Serializable;

public class Msg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int isSuccess;
	private String Warning;
	public Msg(int isSuccess, String warning) {
		super();
		this.isSuccess = isSuccess;
		Warning = warning;
	}
	public Msg() {
		super();
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getWarning() {
		return Warning;
	}
	public void setWarning(String warning) {
		Warning = warning;
	}

	
}
