package com.rong.user.service;

import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.redis.Redis;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.RedisKeyConst;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.PwderrorCountDao;
import com.rong.persist.model.PwderrorCount;

public class PwderrorCountServiceImpl extends BaseServiceImpl<PwderrorCount> implements PwderrorCountService {
	
	private PwderrorCountDao dao=new PwderrorCountDao();
	
	@Override
	public boolean clearLoginPwdError(Integer userId) {
		PwderrorCount  pwderrorCount = dao.getByUserId(userId);
		if(pwderrorCount==null){
			return true;
		}
		if(pwderrorCount.getLoginPwdErrorCount()==0){
			return true;
		}
		pwderrorCount.setLoginPwdErrorCount(0);
		return pwderrorCount.update();
	}
	
	@Override
	public int saveLoginPwdError(Integer userId) {
		PwderrorCount  pwderrorCount = dao.getByUserId(userId);
		if(pwderrorCount==null){
			pwderrorCount = new PwderrorCount();
//			pwderrorCount.setMobile(mobile);
			pwderrorCount.setUserId(userId);
			pwderrorCount.setLoginPwdErrorCount(1);
			pwderrorCount.setPayPwdErrorCount(0);
			pwderrorCount.setCreateTime(new Date());
			pwderrorCount.save();
		}else{
			pwderrorCount.setLoginPwdErrorCount(pwderrorCount.getLoginPwdErrorCount()+1);
			pwderrorCount.setUpdateTime(new Date());
			pwderrorCount.update();
		}
		return pwderrorCount.getLoginPwdErrorCount();
	}
	
	@Override
	public boolean isMaxLoginPwdError(Integer userId) {
		int loginPwdErrorCount = getLoginPwdErrorCount(userId);
//		int max =5;(测试的时候暂时使用)
		int max = Integer.parseInt(Redis.use().hget(RedisKeyConst.SYSCONFMAP, MyConst.loginPwderrorCount).toString());
		if(loginPwdErrorCount<max){
			return false;
		}
		return true;
	}
	
	public int getLoginPwdErrorCount(Integer userId) {
		PwderrorCount  pwderrorCount = dao.getByUserId(userId);
		if(pwderrorCount==null){
			return 0;
		}
		return pwderrorCount.getLoginPwdErrorCount();
	}
	
	/**
	 * 根据盖网号判断支付密码是否错误次数达到最大值
	 * @param mobile 手机号码
	 * @return 说明返回值含义
	 */
	@Override
	public boolean isMaxPayPwdError(Integer userId) {
		PwderrorCount pwderrorCount = dao.getByUserId(userId);
		if (pwderrorCount == null) {
			return false;
		}
		Integer payPwdErrorCount = pwderrorCount.getPayPwdErrorCount();
		Integer max = Integer.parseInt(Redis.use().hget(RedisKeyConst.SYSCONFMAP, MyConst.payPwderrorCount).toString());
		if (payPwdErrorCount < max) {
			return false;
		}
		return true;
	}

	/**
	 * 保存用户支付密码错误信息
	 * @param mobile 手机号码
	 * @return 返回保存后的次数信息
	 */
	@Override
	public Integer savePayPwdError(Integer userId) {
		PwderrorCount pwderrorCount = dao.getByUserId(userId);
		if (pwderrorCount == null) {
			pwderrorCount = new PwderrorCount();
//			pwderrorCount.setMobile(mobile);
			pwderrorCount.setUserId(userId);
			pwderrorCount.setPayPwdErrorCount(1);
			pwderrorCount.setLoginPwdErrorCount(0);
			pwderrorCount.setCreateTime(new Date());
			pwderrorCount.save();
		} else {
			pwderrorCount.setPayPwdErrorCount(pwderrorCount.getPayPwdErrorCount() + 1);
			pwderrorCount.setUpdateTime(new Date());
			pwderrorCount.update();
		}
		return pwderrorCount.getPayPwdErrorCount();
	}

	/**
	 * 根据盖网号清除支付密码错误记录
	 * @param gwNumber 盖网号
	 * @return 说明返回值含义
	 */
	@Override
	public void clearPayPwdError(Integer userId) {
		PwderrorCount pwderrorCount = dao.getByUserId(userId);
		if (pwderrorCount != null && pwderrorCount.getPayPwdErrorCount() != 0) {
			pwderrorCount.setPayPwdErrorCount(0);
			pwderrorCount.update();
		}
	}
	/**
	 * 0点清除全部错误登录信息
	 */
	@Before(Tx.class)
	public void delete(){
		dao.delete();
	}
}
