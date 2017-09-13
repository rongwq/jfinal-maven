package com.jfinal.plugin.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public interface ActiveMqMessageCreator {

	Message create(Session session)throws JMSException;
}

