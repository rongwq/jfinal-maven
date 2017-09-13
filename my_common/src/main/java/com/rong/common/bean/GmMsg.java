package com.rong.common.bean;

import java.util.Map;

public class GmMsg extends Msg {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	private Map response;
	
	private Object responseObject;

	@SuppressWarnings("rawtypes")
	public Map getResponse() {
		return response;
	}

	@SuppressWarnings("rawtypes")
	public void setResponse(Map response) {
		this.response = response;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	
}
