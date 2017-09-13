package com.rong.common.bean;

public class ResultBean {
	private String code;
	private String desc;
	private Object data;
	private String lastId;

	public ResultBean(String code, String desc, Object data, String lastId) {
		super();
		this.code = code;
		this.desc = desc;
		this.data = data;
		this.lastId = lastId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getLastId() {
		return lastId;
	}

	public void setLastId(String lastId) {
		this.lastId = lastId;
	}

}
