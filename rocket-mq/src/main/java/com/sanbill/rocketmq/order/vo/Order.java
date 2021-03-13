package com.sanbill.rocketmq.order.vo;


import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long orderId;
    private String msg;

    public Order(long id, long orderId, String msg) {
        this.id = id;
        this.orderId = orderId;
        this.msg = msg;
    }
}