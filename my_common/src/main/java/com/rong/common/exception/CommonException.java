package com.rong.common.exception;

import java.util.HashMap;
import java.util.Map;


public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private Map<String,Object> bindData = new HashMap<String,Object>();
	
	public CommonException(String code, String message) {
		super(message);
		this.code = code;
	}

	public CommonException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public CommonException(String code, String message, Map<String,Object> bindData) {
		super(message);
		this.code = code;
		this.bindData = bindData;
	}
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getBindData() {
		return bindData;
	}

	public void setBindData(Map<String, Object> bindData) {
		this.bindData = bindData;
	}
	
	public void addBingData(String key , Object value){
		bindData.put(key, value);
	}


}
