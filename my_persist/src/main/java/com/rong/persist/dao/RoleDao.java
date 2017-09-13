package com.rong.persist.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.Admin;
import com.rong.persist.model.AdminRelationRoleResource;
import com.rong.persist.model.AdminResource;
import com.rong.persist.model.AdminRole;

public class RoleDao {
	private AdminRole dao = AdminRole.dao;

	public boolean isExistUserByRname(String rname) {				
		Admin u = Admin.dao.findFirst("SELECT id FROM "+Admin.TABLE+" WHERE role=? ",rname);
		return null!=u?true:false;
	}
	
	public List<AdminRole> getList() {
		String sql = "select * from "+AdminRole.TABLE;
		return  dao.find(sql);
	}
	
	public AdminRole getByName(String name) {
		String sql = "select * from "+AdminRole.TABLE+" where rname =?";
		return  dao.findFirst(sql,name);
	}
	
	public List<AdminResource> getRolePermissions(int roleId) {
		String sql = "select r.id,r.key,r.name from "+AdminResource.TABLE+" r,"+AdminRelationRoleResource.TABLE+" rr where r.id = rr.resId and rr.roleId = ?";
		return  AdminResource.dao.find(sql,roleId);
	}
	
	public boolean saveRolePermissions(int roleId,List<Integer> permissionsIds) {
		Db.update("delete from "+AdminRelationRoleResource.TABLE+" where roleId = ?",roleId);
//		Db.update("11", "123");
		for (Integer resId : permissionsIds) {
			AdminRelationRoleResource model = new AdminRelationRoleResource();
			model.setResId(resId);
			model.setRoleId(roleId);
			model.save();
		}
		return true;
	}
	

	public List<String> getRolesList(){
		List<String> list=new ArrayList<String>();
		List<AdminRole> roles = AdminRole.dao.find("select * from "+AdminRole.TABLE);
		for (AdminRole adminRole : roles) {
			list.add(adminRole.getRname());
		}
		return list;
	}
}
