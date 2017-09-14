package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.redis.Redis;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.RedisKeyConst;
import com.rong.common.util.GenerateSequenceUtil;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.User;
import com.rong.persist.model.Usertoken;

/****
 * @Project_Name:	xk_persist
 * @Copyright:		Copyright © 2012-2017 G-emall Technology Co.,Ltd
 * @Version:		    1.0.0.1
 * @File_Name:		UsertokenDao.java
 * @CreateDate:		2017年7月7日
 * @Designer:		tingting.chen  
 * @Desc:			
 * @ModifyHistory:	
 ****/
public class UsertokenDao extends BaseDao<Usertoken> {

	/**
	 * 获取最新一条token  有缓存
	 * @return
	 */
	public static Usertoken getNewestByUserId(Integer userId) {
		Usertoken usertoken = Redis.use().hget(RedisKeyConst.USERTOKEN_CACHE,userId);
		if (usertoken == null) {
			usertoken = Usertoken.dao.findFirst("SELECT * FROM " + Usertoken.TABLE + " WHERE userId = ? AND status = " + MyConst.USERSTATUS_ENABLE + " ORDER BY id DESC", userId);
		}
		return usertoken;
	}

	public static boolean clearCache(String mobile) {
		Redis.use().hdel(RedisKeyConst.USERTOKEN_CACHE,mobile);
		return true;
	}
	public static String saveToken(User users) {
		String tokenId;
		tokenId = GenerateSequenceUtil.generateSequenceNo();
		Usertoken userToken = new Usertoken();
		userToken.set("userId", users.get("id"));
		userToken.set("mobile", users.getStr("mobile"));
		userToken.set("gwNumber", users.getStr("gwNumber"));
		userToken.set("token", tokenId);
		userToken.set("createTime", System.currentTimeMillis());
		userToken.set("expirTime", System.currentTimeMillis() + MyConst.TOKEN_EXPIR_TIME * 1000);
		userToken.set("status", 1);
		userToken.save();
		return tokenId;
	}
	/**
	 * 根据UserId删除token
	 */
	public static int delAllByUserId(Integer userId) {
		return Db.update("delete from  "+Usertoken.TABLE+"  where userId=?",userId);
	}
	/**
	 * 判断是否有效
	 * @param userId
	 * @param token
	 * @return
	 */
	public static boolean checkIsLoginCache(Integer userId,String token) {
		Usertoken ut = UsertokenDao.getNewestByUserId(userId);
		
		if(null==ut) return false;
		
		if(System.currentTimeMillis()<ut.getLong("expirTime") && ut.getStatus()== Integer.parseInt(MyConst.USERSTATUS_ENABLE) && token.equals(ut.getStr("token"))){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 根据UserId设置token状态
	 */
	public int setStatusByToken(Usertoken userToken) {
		return Db.use("yun").update(
				"update "+Usertoken.TABLE+" set status=? where token=?",
				userToken.getInt("status"), userToken.getStr("token"));
	}
	
	public static int updateMobileByUserId(Integer userId,	String mobile){
		return Db.update(String.format("UPDATE "+Usertoken.TABLE+" SET mobile = ? WHERE id = ?"), mobile, userId);
	}
}
