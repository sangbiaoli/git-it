package com.sanbill.rocketmq.delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 延迟发送消息
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1. 创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("p1");
        //2. 设置namesrvAddr
        producer.setNamesrvAddr("172.168.1.1:9876;172.168.1.2:9876;");
        //3. 启动
        producer.start();
        //4. 生产消息
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("DelayTopic","delay",("hello,world"+i).getBytes());
            //messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 8m 8m 9m 10m 20m 30m 1h 2h";
            msg.setDelayTimeLevel(2);//设置延迟级别
            //同步发送
            SendResult send = producer.send(msg);
            System.out.println(send);
            TimeUnit.SECONDS.sleep(1);
        }
        //5. 关闭生产者
        producer.shutdown();
    }
}