package com.sanbill.activemq;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class Consumer {
    
    private static final Logger LOG = Logger.getLogger(Consumer.class);
    
    // 是否继续响应，可按需由其他逻辑修改值，true：继续响应，false-停止响应
    public static volatile boolean handleFlag = true;

    public static void main(String[] args) {
    	consumeMsg();
    }
    /**
	 * 2018年10月3日
	 * LQB
	 * 步骤如下：
	 * 1.获取工厂-factory
	 * 2.由工厂产生连接-connection，并启动
	 * 3.连接产生会话-session
	 * 4.会话创建队列-queue
	 * 5.会话创建消费者-consumer
	 * 6.消费者获取消息-message，并消费
	 * 
	 * 8.关闭连接
	 */
    private static void consumeMsg() {
    	 ConnectionFactory factory = null; 	//连接工厂
         Connection connection = null;		//连接 
         Session session = null;			//会话
         Destination destination = null;	//消息生产者
         MessageConsumer consumer  = null;	//消费者
         ObjectMessage message = null;		//消息
         try {
         	 factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.141.129:61616");
             connection = factory.createConnection();
             connection.start();
             session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
             destination = session.createQueue("TestQueue");
             consumer = session.createConsumer(destination);
             while(handleFlag) {
                 try {
                	 message = (ObjectMessage)consumer.receive();
                	 if(message == null){
                		 handleFlag = false;
                	 }else{                		 
                		 handleMessage(message);
                	 }
                 } catch (JMSException e) {
                     LOG.error("接收消息出现异常", e);
                 }
             }
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

	/**
     * 处理消息对应的业务
     * @param objectMessage 消息对象
     */
    private static void handleMessage(final ObjectMessage objectMessage) {
        if (objectMessage == null) {
            return;
        }
        
        Object object = null;
        try {
            object = objectMessage.getObject();
            String messageString = (String)object;
            LOG.info("Receive : " + messageString); // 这里仅作打印业务而已
        } catch (JMSException e) {
            LOG.error("获取消息内容出现异常", e);
        }
    }

}