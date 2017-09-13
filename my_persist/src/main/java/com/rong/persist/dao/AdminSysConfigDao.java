package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.AdminSysConfig;

public class AdminSysConfigDao {
	
	private AdminSysConfig dao = AdminSysConfig.dao;
	/**
	 * 查看规则
	 * @return
	 */
	public AdminSysConfig getRule() {
		
		String sql ="select * from "+AdminSysConfig.TABLE+" where `key`='package-rule'";
		return dao.findFirst(sql);
	}
	
	/**
	 * 编辑规则回显数据
	 * @param id
	 * @return
	 */
	public AdminSysConfig getById(Integer id) {
		return dao.findById(id);
	}

	public AdminSysConfig updateRule(Integer id, String value) {
		
		AdminSysConfig c=dao.findById(id);
		c.setValue(value);
		c.update();
		return c;
	}
	/**
	 * 更新奖虚拟励账户
	 * @param money
	 */
	public void updateMoney(String money) {
		String select = "select * from " + AdminSysConfig.TABLE + " where `key`='XK0000RJF' for update";
		Db.find(select);
		String  sql ="UPDATE "+AdminSysConfig.TABLE+" SET `value` = "+money+" + `value` where `key`='XK0000RJF'";
		Db.update(sql);
	}

}
