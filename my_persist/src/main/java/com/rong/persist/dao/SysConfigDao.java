package com.rong.persist.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.model.AdminSysConfig;

public class SysConfigDao {
	private AdminSysConfig dao = AdminSysConfig.dao;
	
	public Page<AdminSysConfig> list(int pageNo, int pageSize, Integer id, String key, String value) {
		String sqlSelect = "select * ";
		StringBuffer sqlWhere = new StringBuffer(" where 1=1");
		if(id!=null){
			sqlWhere.append(" and id= "+id+" ");
		}
		if(!StringUtils.isNullOrEmpty(key)){
			sqlWhere.append(" and `key` = '"+key+"'");
		}
		if(!StringUtils.isNullOrEmpty(value)){
			sqlWhere.append(" and value like '%"+value+"%'");
		}
		String sqlExceptSelect = "from "+AdminSysConfig.TABLE+sqlWhere.toString() +" order by createTime desc";
		return dao.paginate(pageNo, pageSize, sqlSelect, sqlExceptSelect);
	}
	
	public List<AdminSysConfig> getAll() {
		String sql = "select * from "+AdminSysConfig.TABLE+" order by createTime desc";
		return dao.find(sql);
	}
	
	public List<AdminSysConfig> getAppConf() {
		String sql = "select * from "+AdminSysConfig.TABLE+" where type='app' order by createTime desc";
		return dao.find(sql);
	}
	
	public AdminSysConfig getByKey(String key) {
		String sql="select * from "+AdminSysConfig.TABLE+" where `key` = ?";
		return dao.findFirst(sql,key);
	}
	
}
