package com.sanbill.rocketmq.other;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 默认：负载均衡消费方式
 * 生产了三条消息，启动了三个消费者，则每个消费者都消费一条消息
 */
public class BalanceConsumer {
    public static void main(String[] args) throws Exception {
        //1. 创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2. 设置namesrvAddr
        consumer.setNamesrvAddr("172.168.1.1:9876;172.168.1.2:9876;");
        //3. 订阅主题
        consumer.subscribe("topic1","tag1");
        //4. 监听消息并处理
        consumer.setMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            for (MessageExt msg : list) {
                String body = new String(msg.getBody());
                System.out.println(body);
            }
            return null;
        });
        //5. 启动
        consumer.start();
        System.out.println("消费者启动");
    }

}