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
            Message msg = new Message("BatchTopic","tag1",("hello,world"+i).getBytes());
            list.add(msg);
        }

        //把大消息分裂成若干个小的消息
        ListSplitter splitter = new ListSplitter(list);
        while(splitter.hasNext()){
            try{
                List<Message> listItem = splitter.next();
                producer.send(listItem);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //5. 关闭生产者
        producer.shutdown();
    }
}