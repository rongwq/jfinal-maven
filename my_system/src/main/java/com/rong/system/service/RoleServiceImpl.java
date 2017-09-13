package com.rong.system.service;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.rong.persist.dao.RoleDao;
import com.rong.persist.model.AdminResource;
import com.rong.persist.model.AdminRole;

/****
 * @Project_Name:	gxt_admin
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.1.0
 * @File_Name:		RoleServiceImpl.java
 * @CreateDate:		2016年6月7日 上午9:55:30
 * @Designer:		rongwq
 * @Desc:			
 * @ModifyHistory:	
 ****/

public class RoleServiceImpl implements RoleService{
	private RoleDao dao = new RoleDao();
	
	@Override
	public boolean save(AdminRole role) {
		return role.save();
	}

	@Override
	@Before(Tx.class)
	public boolean update(int id, String name, String remark) {
		AdminRole role = AdminRole.dao.findById(id);
		if(name!=null){
			role.setRname(name);
		}
		if(remark!=null){
			role.setRemark(remark);
		}
		return role.update();
	}

	@Override
	public AdminRole getById(int id) {
		return AdminRole.dao.findById(id);
	}

	@Override
	public List<AdminRole> getList() {
		return  dao.getList();
	}

	@Override
	public boolean delete(int id) {
		return AdminRole.dao.deleteById(id);
	}

	@Override
	public AdminRole getByName(String name) {
		return  dao.getByName(name);
	}

	@Override
	public List<AdminResource> getRolePermissions(int roleId) {
		return dao.getRolePermissions(roleId);
	}

	@Override
	public boolean saveRolePermissions(int roleId,List<Integer> permissionsIds) {
		return dao.saveRolePermissions(roleId, permissionsIds);
	}
	
	@Override
	public boolean isExistUserByRname(String rname) {
		return dao.isExistUserByRname(rname);
	}

	@Override
	public List<String> getRolesList() {
		return dao.getRolesList();
	}
}

