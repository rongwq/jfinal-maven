package com.jfinal.plugin.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class ActiveMqManager {
	
	private String user;
	private String password;
	private String host;
	private String port;
	private String failover;
	private boolean useFailover;
	private static final Logger logger = Logger.getLogger(ActiveMqManager.class);
	private ConnectionFactory activeMqConnectionFactory;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public ActiveMqManager(String user, String password,String failover){
		this.user=user;
		this.password = password;
		this.failover = failover;
		this.useFailover=true;
	}
	
	public ActiveMqManager(String user, String password,String host,String port){
		this.user=user;
		this.password = password;
		this.host = host;
		this.port = port;
		this.useFailover=false;
	}
	
	public void init(){
		try{
			/*
			ApplicationContext acx = new FileSystemXmlApplicationContext(PathKit.getWebRootPath()+"/WEB-INF/applicationContext.xml");
			activeMqConnectionFactory=(ActiveMQConnectionFactory)acx.getBean("activeMQConnectionFactory");
			*/
			if(useFailover){
				activeMqConnectionFactory = new ActiveMQConnectionFactory(failover);
			}else{
				activeMqConnectionFactory = new ActiveMQConnectionFactory(user,password,"tcp://"+host+":"+port);
			}
		}catch(Exception ex){
			logger.error("[ActiveMqManager init] error :"+ex.getMessage(),ex);
		}
	}
	
	public void destroy() {
		activeMqConnectionFactory = null;
	}
	
	public  Connection getConnection() throws JMSException{
			return activeMqConnectionFactory.createConnection();
	}
	
	public ConnectionFactory getActiveMqConnectionFactory() {
		return activeMqConnectionFactory;
	}
	public String getFailover() {
		return failover;
	}
	public void setFailover(String failover) {
		this.failover = failover;
	}
	public boolean isUseFailover() {
		return useFailover;
	}
	public void setUseFailover(boolean useFailover) {
		this.useFailover = useFailover;
	}

}
