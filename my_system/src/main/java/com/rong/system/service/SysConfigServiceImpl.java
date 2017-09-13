package com.rong.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.dao.SysConfigDao;
import com.rong.persist.model.AdminSysConfig;

public class SysConfigServiceImpl implements SysConfigService {
	private SysConfigDao dao = new SysConfigDao();
	
	@Override
	public Page<AdminSysConfig> list(int pageNo, int pageSize, Integer id, String key, String value) {
		return dao.list(pageNo, pageSize, id, key, value);
	}
	
	@Override
	public List<AdminSysConfig> getAll() {
		return dao.getAll();
	}
	
	@Override
	public List<AdminSysConfig> getAppConf() {
		return dao.getAppConf();
	}

	@Override
	public AdminSysConfig getById(Integer id) {
		return AdminSysConfig.dao.findById(id);
	}

	@Override
	public AdminSysConfig getByKey(String key) {
		return dao.getByKey(key);
	}

	@Override
	public boolean delete(int id) {
		return AdminSysConfig.dao.deleteById(id);
	}

	/**
	 * 获取系统参数列表
	 * 如：val:String"一：1，二：2，三：3"  ==》 Map {"一=1","二=2","三=3"}
	 * 注意"："和"，"使用中文字符保存
	 */
	@Override
	public Map<String, Object> getMapByKey(String key) {
		AdminSysConfig conf = getByKey(key);
		if(conf==null){
			return null;
		}
		String val = conf.getValue();
		String [] valArr = val.split("，");
		Map<String, Object> map = new HashMap<>();
		for (String str : valArr) {
			map.put(str.split("：")[1], str.split("：")[0]);
		}
		return map;
	}
	
	/**
	 * 获取系统参数列表
	 * 如：val:String"一，二，三"  ==》 List {"一","二","三"}
	 * 注意"："和"，"使用中文字符保存
	 */
	@Override
	public List<Object> getListByKey(String key) {
		AdminSysConfig conf = getByKey(key);
		if(conf==null){
			return null;
		}
		String val = conf.getValue();
		String [] valArr = val.split("，");
		List<Object> list = new ArrayList<>();
		for (String str : valArr) {
			list.add(str);
		}
		return list;
	}


}
