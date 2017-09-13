package com.rong.user.service;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.User;


public interface UserService extends BaseService<User>{
	
	
	//管理后台方法
	public Page<User> getUserList(int page,int pageSize,String userName,String mobile);
	public int resetPassword(Integer id, String md5);
	int setEnable(int id, boolean _switch);

}