package com.rong.system.service;

import java.util.List;

import com.rong.persist.model.AdminResource;
import com.rong.persist.model.AdminRole;

/****
 * @Project_Name:	gxt_admin
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.1.0
 * @File_Name:		RoleService.java
 * @CreateDate:		2016年6月7日 上午9:47:36
 * @Designer:		rongwq
 * @Desc:			用户角色管理
 * @ModifyHistory:	
 ****/

public interface RoleService {
	public boolean save(AdminRole role);
	public boolean update(int id,String name,String remark);
	public boolean delete(int id);
	public AdminRole getById(int id);
	public AdminRole getByName(String name);
	public List<AdminRole> getList();
	public List<AdminResource> getRolePermissions(int roleId);
	public boolean saveRolePermissions(int roleId,List<Integer> permissionsIds);
	public boolean isExistUserByRname(String rname);
	public List<String> getRolesList();
}

