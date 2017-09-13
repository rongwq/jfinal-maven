package com.rong.user.service;

import com.rong.persist.base.BaseService;
import com.rong.persist.model.PwderrorCount;

public interface PwderrorCountService extends BaseService<PwderrorCount>{
	public boolean clearLoginPwdError(Integer userId);
	public int saveLoginPwdError(Integer userId);
	public boolean isMaxLoginPwdError(Integer userId);
	public boolean isMaxPayPwdError(Integer userId);
	public Integer savePayPwdError(Integer userId);
	public void clearPayPwdError(Integer userId);
	/**
	 * 每天0点清楚全部数据
	 */
	public void delete();
}
