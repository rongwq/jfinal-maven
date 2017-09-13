package com.rong.persist.dao;

import java.util.List;

import com.gw.common.util.StringUtils;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.AdminResource;

public class ResourceDao {
	private AdminResource dao = AdminResource.dao;
	
	public Page<AdminResource> getPage(int pageNo, int pageSize, String key, Integer id, String name) {
		StringBuffer where = new StringBuffer(" where 1=1");
		if(!StringUtils.isNullOrEmpty(key)){
			where.append(" and `key` = '"+key+"'");
		}
		if(id!=null){
			where.append(" and (pid = "+id+" or id="+id+")");
		}
		if(!StringUtils.isNullOrEmpty(name)){
			where.append(" and name like '%"+name+"%'");
		}
		String select = "select *";
		String sqlExceptSelect = "from "+AdminResource.TABLE;
		return dao.paginate(pageNo, pageSize, select, sqlExceptSelect+where.toString()+" order by createTime desc");
	}
	
	public List<AdminResource> getAll() {
		String sql = "select * from "+AdminResource.TABLE;
		return dao.find(sql);
	}
	
	public AdminResource getByKey(String key) {
		String sql = "select * from "+AdminResource.TABLE+" where `key` = ?";
		return dao.findFirst(sql,key);
	}
	
	public List<AdminResource> getMenus() {
		String sql = "select * from "+AdminResource.TABLE+" where type = 1";
		return dao.find(sql);
	}
}
