package com.rong.common.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ResultObjectWithToken extends BaseTemplate implements Serializable{
	private Object resultData;
	
	public ResultObjectWithToken() {
		super();
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(String token,Object resultData) {
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("user", resultData);
		this.resultData = map;
	}
	
}
