package com.rong.system.service;

import java.util.List;
import java.util.Set;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.Admin;

public interface AdminService {
	public Page<Admin> getList(int page,int pagesize);
	public boolean save();
	public Admin getByUserName(String username);
	public Set<String> findPermissions(String username);
	public List<Admin> getAllUser();
	public List<Admin> getUserList();
}
 