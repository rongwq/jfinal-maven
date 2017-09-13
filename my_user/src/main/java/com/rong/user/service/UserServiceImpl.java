package com.rong.user.service;

import com.gw.common.exception.CommonException;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.MyConst;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.UserDao;
import com.rong.persist.model.User;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	
	private UserDao dao = new UserDao();
	@Override
	public Page<User> getUserList(int page,int pagesize,String userName,String mobile) {
		return dao.getUserList(page,pagesize,userName,mobile);
	}
	
	public int setState(int id, String userstate){
		return dao.updateField(id, "UserState", userstate);
	}
	
	
	public int setEnable(int id, boolean _switch) throws  CommonException{
	
		return setState(id,  _switch ? MyConst.USERSTATUS_ENABLE : MyConst.USERSTATUS_DISABLE);
	}
	
	@Override
	public int resetPassword(Integer id, String md5) {
		// TODO Auto-generated method stub
		return dao.updateField(id, "userPassword", md5);
	}
}
