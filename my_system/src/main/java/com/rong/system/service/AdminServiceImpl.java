package com.rong.system.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.rong.persist.dao.AdminDao;
import com.rong.persist.model.Admin;

public class AdminServiceImpl implements AdminService {
	
	private AdminDao dao = new AdminDao();
	
	@Override
	public Page<Admin> getList(int page,int pagesize) {
		return dao.getList(page,pagesize);
	}
	
	@Override
	@Before(Tx.class)
	public boolean save(){
		Admin admin = new Admin();
		admin.setUserName("123");
		admin.setPassword("123456");
		admin.setRole("1");
		admin.setSalt("1");
		admin.setCreateTime(new Date()); 
		if (admin.save()) {			
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public Admin getByUserName(String username) {
		return dao.getByUserName(username);
	}

	@Override
	public Set<String> findPermissions(String username) {
		return dao.findPermissions(username);
	}

	@Override
	public List<Admin> getAllUser() {
		return dao.getAllUser();
	}
	@Override
	public List<Admin> getUserList() {
		
		return dao.getAdminList();
	}


}
