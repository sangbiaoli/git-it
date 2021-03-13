package com.sanbill.rocketmq.order;



import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 顺序消费
 * 生产了三条消息，启动了三个消费者，则每个消费者都消费一条消息
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //1. 创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2. 设置namesrvAddr
        consumer.setNamesrvAddr("172.168.1.1:9876;172.168.1.2:9876;");
        //3. 订阅主题
        consumer.subscribe("OrderTopic", "*");
        //4. 监听消息并处理，一个队列交给一个线程处理
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("线程名称" + Thread.currentThread().getName() + "消费消息：" + new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //5. 启动
        consumer.start();
        System.out.println("消费者启动");
    }

}