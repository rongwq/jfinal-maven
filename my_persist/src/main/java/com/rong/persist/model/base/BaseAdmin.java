package com.rong.persist.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAdmin<M extends BaseAdmin<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setUserName(java.lang.String userName) {
		set("userName", userName);
	}

	public java.lang.String getUserName() {
		return getStr("userName");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return getStr("password");
	}

	public void setSalt(java.lang.String salt) {
		set("salt", salt);
	}

	public java.lang.String getSalt() {
		return getStr("salt");
	}

	public void setRole(java.lang.String role) {
		set("role", role);
	}

	public java.lang.String getRole() {
		return getStr("role");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public void setTrueName(java.lang.String trueName) {
		set("trueName", trueName);
	}

	public java.lang.String getTrueName() {
		return getStr("trueName");
	}

	public void setMobile(java.lang.String mobile) {
		set("mobile", mobile);
	}

	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public void setEmail(java.lang.String email) {
		set("email", email);
	}

	public java.lang.String getEmail() {
		return getStr("email");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public void setSex(java.lang.Integer sex) {
		set("sex", sex);
	}

	public java.lang.Integer getSex() {
		return getInt("sex");
	}

}