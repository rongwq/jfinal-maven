package com.rong.job.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.rong.common.bean.MyConst;
import com.rong.common.util.PropertiesUtils;

public class Log4jConfigListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	    ServletContext sc = sce.getServletContext();
		String logPath = sc.getRealPath("/WEB-INF");
		System.setProperty("logPath",logPath);
		int mode = Integer.parseInt(PropertiesUtils.get("RUNNING_MODE","1"));
		myLoadPropertyFile(mode);
		// 初始化log4j连接数据库配置
		System.setProperty("jdbc", PropertiesUtils.get("log.jdbcUrl", null));
		System.setProperty("user", PropertiesUtils.get("user", null));
		System.setProperty("password", PropertiesUtils.get("password", null));
		PropertiesUtils.load("/conf.properties");
	}
	
	private boolean myLoadPropertyFile(int mode) {
		switch (mode) {
		case MyConst.RUNNING_MODE_DEV_SERVER:
			PropertiesUtils.load("/config_dev.txt");
			break;

		case MyConst.RUNNING_MODE_TEST_SERVER:
			PropertiesUtils.load("/config_test.txt");
			break;

		case MyConst.RUNNING_MODE_ONLINE_SERVER:
			PropertiesUtils.load("/config_online.txt");
			break;

		}
		return true;
	}

}
