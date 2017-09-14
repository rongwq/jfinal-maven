package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.Usertoken;

public class UsertokenExpirClearJob implements Job{
	private static final Logger logger = Logger.getLogger(UsertokenExpirClearJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始清理用户过期token数据流程");
		try{
			String sql = "delete from "+Usertoken.TABLE +" where expirTime < ? ";
			long newTime  = System.currentTimeMillis();
			Db.update(sql, newTime);
			logger.info("定时清理用户过期token数据成功，流程结束");
		}catch(Exception e){
			logger.info("定时清理用户过期token数据出现异常");
		}		
	}
}
