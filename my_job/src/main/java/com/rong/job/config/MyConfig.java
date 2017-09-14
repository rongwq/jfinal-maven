package com.rong.job.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.quartz.QuartzPlugin3;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import com.rong.common.bean.MyConst;
import com.rong.persist.model._MappingKit;

public class MyConfig extends JFinalConfig {

	/**
	 * 加载配置文件
	 * 
	 * @param mode
	 * @return
	 */
	private boolean myLoadPropertyFile(int mode) {
		switch (mode) {
		case MyConst.RUNNING_MODE_DEV_SERVER:
			loadPropertyFile("config_dev.txt");
			break;

		case MyConst.RUNNING_MODE_TEST_SERVER:
			loadPropertyFile("config_test.txt");
			break;

		case MyConst.RUNNING_MODE_ONLINE_SERVER:
			loadPropertyFile("config_online.txt");
			break;

		}
		return true;
	}

	@Override
	public void configConstant(Constants me) {
		myLoadPropertyFile(MyConst.RUNNING_MODE);
		MyConst.devMode = getPropertyToBoolean("devMode", false);
		me.setDevMode(MyConst.devMode);
	}

	@Override
	public void configRoute(Routes me) {
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configPlugin(Plugins me) {
		final String username = getProperty("user");
		final String password = getProperty("password").trim();
		final String instance_read_source1_jdbcUrl = getProperty("jdbcUrl");
		dataSourceConfig(me, instance_read_source1_jdbcUrl, username, password);
		
		RedisPlugin redisPlugin = new RedisPlugin("redis", getProperty("redis.host"), getPropertyToInt("redis.port"), getProperty("redis.password"));
		me.add(redisPlugin);
		
		QuartzPlugin3 quartzPlugin =  new QuartzPlugin3("job.properties");
		me.add(quartzPlugin);

	}
	
	
	private void dataSourceConfig(Plugins me, String source1_url, String username, String password) {
		//1.主库
		DruidPlugin druidPlugin = new DruidPlugin(source1_url, username, password);
		druidPlugin.setDriverClass("com.mysql.jdbc.Driver");
		druidPlugin.setInitialSize(2).setMaxActive(300).setMinIdle(50).setTestOnBorrow(false).setMaxWait(1000);
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin("yun", druidPlugin);
		if (MyConst.devMode) {
			arp.setShowSql(true);
		}
		_MappingKit.mapping(arp);
		me.add(arp);

		//2.连接到log库
		final String logjdbcurl = getProperty("log.jdbcUrl");
		DruidPlugin logDruid = new DruidPlugin(logjdbcurl, username, password);
		logDruid.setDriverClass("com.mysql.jdbc.Driver");
		logDruid.setInitialSize(2).setMaxActive(300).setMinIdle(50).setTestOnBorrow(false).setMaxWait(1000);
		me.add(logDruid);
		// 配置ActiveRecord插件
		ActiveRecordPlugin logArp = new ActiveRecordPlugin("log", logDruid);
		if (MyConst.devMode) {
			logArp.setShowSql(true);
		}
		me.add(logArp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterJFinalStart() {
		System.out.println("job 启动成功");
		super.afterJFinalStart();
	}
	
	

}
