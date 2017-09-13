package com.jfinal.plugin.activemq;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.plugin.IPlugin;

public class ActiveMqPlugin  implements IPlugin{
	
	private ActiveMqManager activeMqManager;
	
	private List<RoutesBuilder> routeList;
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveMqPlugin.class);
	
	public ActiveMqPlugin(String userName , String password ,String host,String port){
		this.activeMqManager = new ActiveMqManager(userName,password,host,port);
		this.routeList = new ArrayList<RoutesBuilder>();
	}
	
	public ActiveMqPlugin(String userName , String password ,String failover){
		this.activeMqManager = new ActiveMqManager(userName,password,failover);
		this.routeList = new ArrayList<RoutesBuilder>();
	}
	

	@Override
	public boolean start() {
		activeMqManager.init();
		ActiveMqKit.init(activeMqManager);
		
		CamelContext context = new DefaultCamelContext(); 
		JmsComponent jmsComponentAutoAcknowledge = JmsComponent.jmsComponentAutoAcknowledge(activeMqManager.getActiveMqConnectionFactory());
		context.addComponent("jms", jmsComponentAutoAcknowledge);
	            
		try {
			if(null!=routeList&&routeList.size()>0){
				for(RoutesBuilder route : routeList){
					context.addRoutes(route);
				}
			}
			context.start();
		} catch (Exception ex) {
			logger.error("[ActiveMqPlugin] "+ex.getMessage(),ex);
		}
		
		return true;
	}

	@Override
	public boolean stop() {
		activeMqManager.destroy();
		return true;
	}
	
	public void addRoute(RoutesBuilder route){
		routeList.add(route);
	}

}
