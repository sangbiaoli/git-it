package com.sanbill.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;


public class Producer {
    
    private static final Logger LOG = Logger.getLogger(Producer.class);
    
    public static void main(String[] args) {
    	produceMsg();
    }

	/**
	 * 2018年10月3日
	 * LQB
	 * 步骤如下：
	 * 1.获取工厂-factory
	 * 2.由工厂产生连接-connection，并启动
	 * 3.连接产生会话-session
	 * 4.会话创建队列-queue
	 * 5.会话创建生产者-producer
	 * 6.会话创建消息-message
	 * 7.创建者发送消息
	 * 
	 * 8.关闭连接
	 */
	private static void produceMsg() {
        ConnectionFactory factory = null; 	//连接工厂
        Connection connection = null;		//连接 
        Session session = null;				//会话
        Destination destination = null;		//消息生产者
        MessageProducer producer = null;	//创建队列
        ObjectMessage message = null;		//消息
        try {
        	factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.141.129:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("TestQueue");
            producer = session.createProducer(destination);
            message = session.createObjectMessage("hello world...");
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(message);
            session.commit();
            LOG.info("send msg:"+message.getObject());
        } catch (JMSException e) {
            LOG.error("出现异常", e);
        }
        
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                LOG.error("关闭连接出现异常", e);
            }
        }
		
	}

}