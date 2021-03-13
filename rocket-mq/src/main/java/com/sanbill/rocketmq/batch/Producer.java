package com.sanbill.rocketmq.batch;



import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步发送消息
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1. 创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2. 设置namesrvAddr
        producer.setNamesrvAddr("172.168.1.1:9876;172.168.1.2:9876;");
        //3. 启动
        producer.start();
        List<Message> list = new ArrayList<Message>();
        //4. 生产消息
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("top1","tag1",("hello,world"+i).getBytes());
            list.add(msg);
        }
        producer.send(list);
        //5. 关闭生产者
        producer.shutdown();
    }
}