package com.sanbill.rocketmq.other;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 单向发送消息
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        //1. 创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("p1");
        //2. 设置namesrvAddr
        producer.setNamesrvAddr("172.168.1.1:9876;172.168.1.2:9876;");
        //3. 启动
        producer.start();
        //4. 生产消息
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("top1","tag1",("hello,world"+i).getBytes());
            //单向发送
            producer.sendOneway(msg);
            TimeUnit.SECONDS.sleep(1);
        }
        //5. 关闭生产者
        producer.shutdown();
    }
}