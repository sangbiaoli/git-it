package com.sanbill.rocketmq.producer;


import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 异步步发送消息
 */
public class AsyncProducer {
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
            //异步发送
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable.getCause());
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
        //5. 关闭生产者
        producer.shutdown();
    }
}