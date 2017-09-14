package com.rong.job.myjob;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.ICallback;
import com.rong.common.util.DateTimeUtil;

/****
 * @Copyright:		Copyright © 2012-2017 g-emall Technology Co.,Ltd
 * @Version:		1.0.0
 * @File_Name:		LogTableCreateJob.java
 * @CreateDate:		2017年7月7日 上午11:32:18
 * @Designer:		rongwq
 * @Desc:			创建日志表（按月）
 * @ModifyHistory:	
 ****/

public class LogTableCreateJob implements Job{
	private static final Logger logger = Logger.getLogger(LogTableCreateJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Db.use("log").execute(new ICallback() {
			@Override
			public Object call(Connection conn) throws SQLException {
				StringBuffer sql = new StringBuffer();
				String date = DateTimeUtil.formatDateTime(DateTimeUtil.nextMonthDate(), "yyyyMM");
				sql.append("CREATE TABLE `log_"+date+"`(");
				sql.append("`id` bigint(20) NOT NULL AUTO_INCREMENT,");
				sql.append("`logFrom` varchar(20) DEFAULT NULL,");
				sql.append("`userId` varchar(100) DEFAULT NULL,");
				sql.append("`class` varchar(128) DEFAULT NULL,");
				sql.append("`method` varchar(64) DEFAULT NULL,");
				sql.append("`create_time` datetime DEFAULT NULL,");
				sql.append("`log_level` varchar(20) DEFAULT NULL,");
						sql.append("`msg` text,");
						sql.append("PRIMARY KEY (`id`)");
						sql.append(") ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8");
				conn.createStatement().execute(sql.toString());
				logger.info("创建日志表成功：log_"+date);
				return null;
			}
		});
		
	}
}

