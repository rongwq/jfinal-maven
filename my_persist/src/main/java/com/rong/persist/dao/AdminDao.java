package com.rong.persist.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.Admin;
import com.rong.persist.model.AdminRelationRoleResource;
import com.rong.persist.model.AdminResource;
import com.rong.persist.model.AdminRole;

public class AdminDao {
	private Admin dao = Admin.dao;
	public Page<Admin> getList(int page,int pagesize) {
		String select = "select *";
		String sqlExceptSelect = "from "+Admin.TABLE;
		return dao.paginate(page, pagesize, select, sqlExceptSelect);
	}
	
	public Admin getByUserName(String userName) {
		String sql = "select * from " + Admin.TABLE + " where userName = ?";
		Admin admin = dao.findFirst(sql, userName);
		return admin;
	}
	
	public Set<String> findPermissions(String userName) {
		Admin user = getByUserName(userName);
		if(user!=null){
			String sql = "select r.key from "+AdminResource.TABLE+" r,"+AdminRelationRoleResource.TABLE+" rr,"+AdminRole.TABLE+" ro where r.id = rr.resId and rr.roleId = ro.id and ro.rname=?";
			List<AdminResource>  list = AdminResource.dao.find(sql,user.getRole());
			Set<String> set = new HashSet<String>();
			for (AdminResource res : list) {
				set.add(res.getKey());
			}
			return set;
		}
		return null;
	}
	 
	public List<Admin> getAllUser(){
		String sql = "select * from " + Admin.TABLE + " where role != 'admin'";
		List<Admin> list = dao.find(sql);
		return list;
	}
	public  List<Admin> getAdminList(){
		
		List<Admin> adminList = Admin.dao.find("select * from "+Admin.TABLE);
		
		return adminList;
	}
}
