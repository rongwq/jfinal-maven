package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.PwderrorCount;

public class PwderrorCountDelJob implements Job{
	private static final Logger logger = Logger.getLogger(PwderrorCountDelJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始清理密码错误次数数据流程");
		try{
			Db.update("delete from "+PwderrorCount.TABLE +" where id>0");
			logger.info("定时清理密码错误次数记录表成功，流程结束");
		}catch(Exception e){
			logger.info("定时清理密码错误次数记录出现异常");
		}		
	}
}
