package com.rong.admin.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin3;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.tx.TxByActionKeys;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.rong.admin.controller.AdminController;
import com.rong.admin.controller.IndexController;
import com.rong.admin.controller.LogController;
import com.rong.admin.controller.ResourceController;
import com.rong.admin.controller.RoleController;
import com.rong.admin.controller.SysConfigController;
import com.rong.admin.controller.UserController;
import com.rong.common.bean.MyConst;
import com.rong.persist.model._MappingKit;

public class MyConfig extends JFinalConfig {
	/**
	 * 供Shiro插件使用。
	 */
	Routes routes;

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
		me.setViewType(ViewType.JSP);
		me.setEncoding("UTF8");
//		me.setBaseUploadPath(File.separator);// 設定文件保存，使用默认路径，不需要设置
		me.setError404View("/views/common/404.jsp");
		me.setError500View("/views/common/500.jsp");
		me.setErrorView(401, "/views/login.jsp");
		me.setErrorView(403, "/views/login.jsp");
		initConst();
	}

	/**
	 * 初始刷常量
	 */
	public void initConst() {
		MyConst.upload = getProperty("upload");
		MyConst.imgUrlHead = getProperty("imgUrlHead");
		MyConst.ftp_host = getProperty("ftp_host");
		MyConst.ftp_port = getPropertyToInt("ftp_port");
		MyConst.ftp_username = getProperty("ftp_username");
		MyConst.ftp_pwd  = getProperty("ftp_pwd");
		MyConst.ftp_uploads = getProperty("ftp_uploads");
		MyConst.ftp_files = getProperty("ftp_files");
		MyConst.jpush_appkey = getProperty("jpush_appkey");
		MyConst.jpush_masterSecret = getProperty("jpush_masterSecret");
	}
	
	@Override
	public void configRoute(Routes me) {
		this.routes = me;
		me.add("/", IndexController.class);
		me.add("/admin", AdminController.class);
		me.add("/role", RoleController.class);
		me.add("/resource", ResourceController.class);
		me.add("/sysConfig", SysConfigController.class);
		me.add("/log", LogController.class);
		me.add("/user",UserController.class);
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

		RedisPlugin redisPlugin = new RedisPlugin("redis", getProperty("redis.host"), getPropertyToInt("redis.port"),
				getProperty("redis.password"));
		me.add(redisPlugin);
		// 添加shiro
		ShiroPlugin3 shiroPlugin = new ShiroPlugin3(this.routes);
		me.add(shiroPlugin);

	}

	private void dataSourceConfig(Plugins me, String source1_url, String username, String password) {
		// 1.主库
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

		// 2.连接到log库
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
		me.add(new CommonInterceptor());
		me.add(new TxByActionKeys("save", "update", "delete"));
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterJFinalStart() {
		System.out.println("admin 启动成功");
		super.afterJFinalStart();
	}

}
