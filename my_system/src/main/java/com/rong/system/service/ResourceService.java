package com.rong.system.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.AdminResource;

/****
 * @Project_Name:	gxt_admin
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.1.0
 * @File_Name:		PermissionsService.java
 * @CreateDate:		2016年6月7日 下午2:08:51
 * @Designer:		rongwq
 * @Desc:			
 * @ModifyHistory:	
 ****/

public interface ResourceService {
	public boolean save(AdminResource resource);
	public boolean delete(int id);
	public boolean update(AdminResource resource);
	public Page<AdminResource> getPage(int pageNo,int pageSize,String key,Integer id,String name);
	public List<AdminResource> getAll();
	public AdminResource getByKey(String key);
	public AdminResource getById(Integer id);
	public List<AdminResource> getMenus(); 
}

