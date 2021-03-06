package com.rong.persist.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.StringUtils;
import com.rong.persist.model.User;

/**
 * Generated by JFinal.
 */


public class UserDao  {

	public static final User dao = new User();

	/** 表名 **/
	public static String TABLE = "xk_user";

	public static String UserFields(){
		return "id,userNickName,userHead,mobile,userPassword,payPassword,"
		+"userType,registerDate,referrals,code,userSex,userState,gwNumber,supCode,loginTime,oneButtonStart,autoOrders ";
	}
	
	public User getUserByMobile(String mobile){
		String sql = "select * from " + User.TABLE+ " where mobile =? ";
		return  dao.findFirst(sql, mobile);
	}

	public User getUserByID(Integer userId){
		String sql = "select * from " + User.TABLE+ " where id =? ";
		return  dao.findFirst(sql, userId);
	}

	

	public User getUserByOpenid(String openid){
		String sql = "select * from " + User.TABLE+ " where openid =? ";
		return  dao.findFirst(sql, openid);
	}
	

	/**
	 * @date 2017-7-7
	 * @return user
	 * @param mobile
	 * @param decrptyPwd
	 * @dec 获取用户信息
	 * */
	public User getByMobileAndPassword(String mobile, String decrptyPwd){
		String sql = "select "+UserDao.UserFields()+" from " + User.TABLE+ " where mobile=? and userPassword=?";
		return dao.findFirst(sql, mobile,decrptyPwd);
	}
//	@Override
//	public Map<String, Object> clone() {
//		Map<String, Object> attrs  = this.getAttrs();
//	    return attrs; //重新生成一个对象，并用当前对象的属性初始化新对象
//	}
//	

	/* private */
	public static String getMD5(byte[] src) {
		StringBuffer sb = new StringBuffer();
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(src);
			for (byte b : md.digest())
				sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(
						Integer.toString(b & 0xF, 16));
		} catch (NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param page
	 * @param pagesize
	 * @param userName 用户名
	 * @param mobile 手机号 条件搜索
	 * 查询用户端 用户列表信息 20170712 yong.pei
	 */
	public Page<User> getUserList(int page,int pagesize,String userName,String mobile) {
		StringBuffer where = new StringBuffer(" where 1=1");
		if(!StringUtils.isNullOrEmpty(userName)){
			where.append(" and userNickName like '%"+userName+"%'  ");
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			where.append(" and mobile like '%"+mobile+"%' ");
		}
		String select = "select *";
		String sqlExceptSelect = "from "+User.TABLE;
		return dao.paginate(page, pagesize, select, sqlExceptSelect+where.toString()+" AND userType = 1 "+" order by registerDate desc");
	}
	
	public int updateField(int id, String fieldName, Object value){
		return Db.update(String.format("UPDATE %s SET %s = ? WHERE id = ?", User.TABLE, fieldName), value, id);
	}

	public User getUserByCode(String code) {
		String sql = "select * from " + User.TABLE+ " where code =? ";
		return  dao.findFirst(sql, code);
	}

	public List<User> getUser() {
		String sql= "select * from "+User.TABLE+" where userType=2";
		return dao.find(sql);
	}

	public Record getBusiness(String code) {
		//根据传入的 代理商 code 查询出商家的 code
		String sql = "select `code` from "+User.TABLE+" where userType=3 and supCode = ?";
		List<Record> list= Db.find(sql, code);
		//获取 businessmen list.size 为个数 显示给前端
		int businessmen = list.size();
		int washMan = 0;
		//满足 商家不为空 并且个数大于0的条件  查询商家下面的洗车员
		if(null != list && list.size()>0){
			for(Record re:list){
				String sql2 ="select count(*) as washMan from "+User.TABLE+" where userType=2 and supCode = ? ";
				Record record = Db.findFirst(sql2,re.get("code"));
				//转换为string类型
				 washMan += new Integer(String.valueOf(record.get("washMan")));
			}
		}
		Record rs = new Record();
		rs.set("businessmen", businessmen);
		rs.set("washMan", washMan);
		rs.set(sql, list);
		return rs;
	}

	public Record getWashManFromBusiness(String code) {
		int washMan = 0;
		String sql ="select count(*) as washMan from "+User.TABLE+" where userType=2 and supCode = ? ";
		Record record = Db.findFirst(sql,code);
	    washMan += new Integer(String.valueOf(record.get("washMan")));
		Record rs = new Record();
		rs.set("washMan", washMan);
		return rs;
	}

	public boolean deleteUserByID(Integer userID){
		return dao.deleteById(userID);
	}

	public List<Record> getAllUser(Integer  platform) {
		if(platform==1){
			String sql ="select * from "+User.TABLE+" where userType=1 and userState=1";
			return Db.find(sql);
		}else if (platform==2) {
			String sql ="select * from "+User.TABLE+" where (userType=2 or userType=3 or userType=4) and userState=1";
			return Db.find(sql);
		}else {
			String sql ="select * from "+User.TABLE+" where  userState=1";
			return Db.find(sql);
		}
	}
	
	public User getUserCode(String code){
		String sql = "select id,supCode from "+User.TABLE+" where userState= 1 and supCode=?";
		return dao.findFirst(sql,code);
	}
	

}
