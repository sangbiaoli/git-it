package com.sanbill.rocketmq.vo;

import java.util.ArrayList;
import java.util.List;

public class OrderHelper {

    public static List<Order> buildeOrders() {
        List<Order> list = new ArrayList<>();
        list.add(new Order(1l,1l,"张三下单"));
        list.add(new Order(2l,1l,"张三付款"));
        list.add(new Order(3l,1l,"张三生产"));
        list.add(new Order(4l,1l,"张三完成"));

        list.add(new Order(5l,2l,"李四下单"));
        list.add(new Order(6l,2l,"李四付款"));
        list.add(new Order(7l,2l,"李四生产"));
        list.add(new Order(8l,2l,"李四完成"));
    }
}
