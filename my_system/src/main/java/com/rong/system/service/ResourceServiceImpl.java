package com.rong.system.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.dao.ResourceDao;
import com.rong.persist.model.AdminResource;

/****
 * @Project_Name:	apart
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.1.0
 * @File_Name:		ResourceServiceImpl.java
 * @CreateDate:		2016年6月7日 下午2:14:02
 * @Designer:		rongwq
 * @Desc:			
 * @ModifyHistory:	
 ****/

public class ResourceServiceImpl implements ResourceService{
	ResourceDao dao = new ResourceDao();
	
	@Override
	public boolean save(AdminResource resource) {
		return resource.save();
	}

	@Override
	public boolean delete(int id) {
		return AdminResource.dao.deleteById(id);
	}

	@Override
	public boolean update(AdminResource resource) {
		return resource.update();
	}

	@Override
	public Page<AdminResource> getPage(int pageNo, int pageSize, String key, Integer id, String name) {
		return dao.getPage(pageNo, pageSize, key, id, name);
	}

	@Override
	public List<AdminResource> getAll() {
		return dao.getAll();
	}

	@Override
	public AdminResource getByKey(String key) {
		return dao.getByKey(key);
	}

	@Override
	public AdminResource getById(Integer id) {
		return AdminResource.dao.findById(id);
	}

	@Override
	public List<AdminResource> getMenus() {
		return dao.getMenus();
	}

}

