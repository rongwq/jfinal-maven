package com.rong.user.service;

import com.rong.persist.base.BaseService;
import com.rong.persist.model.User;

public interface UserApiService extends BaseService<User>{
	public User getUserByMobile(String mobile);
	public User getUserByID(Integer userID);
	public User getUserByOpenid(String openid);
	public int userInfoSave(User user);
	public User getByMobileAndPassword(String mobile, String password) ;
	public boolean setUserPayPassword(String mobile, String payPassword);
	public boolean updateUserHead(Integer userId,String userHead) ;
	public boolean updateMobile(User user,String updateMobile) ;
	public boolean updateUserName(String mobile, String userName);
	public boolean updateLoginTime(User user);

	public String checkPayPassword(Integer userId, String payPassword);
	public User getUserByCode(String code);
	public boolean deleteUserByID(Integer userID);

}

