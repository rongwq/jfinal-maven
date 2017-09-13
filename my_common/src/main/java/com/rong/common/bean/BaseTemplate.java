package com.rong.common.bean;

public class BaseTemplate {
	private String resultCode;
	private String resultDes;
	private Object resultData;
	
	public BaseTemplate() {
		super();
	}
	
	
	public BaseTemplate(String resultCode, String resultDes) {
		super();
		this.resultCode = resultCode;
		this.resultDes = resultDes;
	}
	
	public BaseTemplate(String resultCode, String resultDes,Object resultData) {
		super();
		this.resultCode = resultCode;
		this.resultDes = resultDes;
		this.resultData = resultData;
	}


	public String getResultCode() {
		return resultCode;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}


	public String getResultDes() {
		return resultDes;
	}


	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}


	public Object getResultData() {
		return resultData;
	}


	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	
}
