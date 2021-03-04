package com.sanbill.rocketmq.producer;


import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.sanbill.rocketmq.vo.Order;
import com.sanbill.rocketmq.vo.OrderHelper;

import java.util.List;

/**
 * 顺序发送消息
 * 场景解析：
 * 张三：下单，付款，生产，完成
 * 李四：下单，付款，生产，完成
 * 对于同一个人，这四个步骤是严格按顺序来的，但是broker有多个queue，
 * 因此为了保证顺序，我们要把同一个订单的四个消息发送到同一个queue中
 */
public class OrderProducer {
    public static void main(String[] args) throws Exception {
        //1. 创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("p1");
        //2. 设置namesrvAddr
        producer.setNamesrvAddr("172.168.1.1:9876;172.168.1.2:9876;");
        //3. 启动
        producer.start();
        //4. 生产消息
        List<Order> list =  OrderHelper.buildeOrders();
        for (int i = 0; i < list.size(); i++) {
            Order order = list.get(i);
            Message msg = new Message("top1","tag1",order.getMsg().getBytes());
            producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Long orderId = (Long) arg;
                    int index = (int) (orderId % mqs.size());//用订单id对队列大小取模，保证相同订单消息放在同一个队列中
                    return mqs.get(index);
                }
            },order.getOrderId());
        }
        //5. 关闭生产者
        producer.shutdown();
    }
}