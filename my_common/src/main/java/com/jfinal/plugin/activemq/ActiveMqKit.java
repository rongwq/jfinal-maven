package com.jfinal.plugin.activemq;

import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

public class ActiveMqKit {

	private static final Logger logger = Logger.getLogger(ActiveMqKit.class);

	private static volatile ActiveMqManager activeMqManager;

	public static ActiveMqManager getActiveMqManager() {
		return activeMqManager;
	}

	public static void init(ActiveMqManager activeMqManager) {
		ActiveMqKit.activeMqManager = activeMqManager;
	}

	public static void send(String destionation, boolean transacted, int acknowledge, int persistent, ActiveMqMessageCreator ceator) {
		Connection connection = null;
		try {
			connection = activeMqManager.getConnection();
			connection.start();
			// 获取操作连接
			Session session = connection.createSession(transacted, acknowledge);
			Destination destination = session.createQueue(destionation);
			// 得到消息生成者【发送者】
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(persistent);
			producer.send(ceator.create(session));
			connection.close();
		} catch (Exception e) {
			logger.error("[ActiveMqKit send] " + e.getMessage(), e);
		} finally {
			if (null != connection) {
				try {
					connection.close();
				} catch (JMSException e) {
					logger.error("[ActiveMqKit send] " + e.getLocalizedMessage(), e);
				}
			}
		}
	}

	public static void send(String destionation, ActiveMqMessageCreator ceator) {
		try {
			send(destionation, false, Session.AUTO_ACKNOWLEDGE, DeliveryMode.PERSISTENT, ceator);
		} catch (Exception e) {
			logger.error("[ActiveMqKit send] " + e.getMessage(), e);
		}
	}

	public static void send(String destionation, final Map<String, Object> params) {
		send(destionation, false, Session.AUTO_ACKNOWLEDGE, DeliveryMode.PERSISTENT, new ActiveMqMessageCreator(){
			@Override public Message create(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				for (Entry<String, Object> entry : params.entrySet()) {
					message.setObjectProperty(entry.getKey(), entry.getValue());
				}
				return message;
			}
		});
	}


}
