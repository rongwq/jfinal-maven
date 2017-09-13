package com.rong.system.service;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.AdminSysConfig;

public interface SysConfigService {
	public Page<AdminSysConfig> list(int pageNo,int pageSize,Integer id,String key,String value);
	public List<AdminSysConfig> getAll();
	public List<AdminSysConfig> getAppConf();
	public AdminSysConfig getById(Integer id);
	public AdminSysConfig getByKey(String key);
	public boolean delete(int id);
	public Map<String, Object> getMapByKey(String key);
	public List<Object> getListByKey(String key);
}
