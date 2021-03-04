package com.sanbill.rocketmq.vo;

public class Order {
    private Long id;
    private Long orderId;
    private String msg;

    public Order(long id, long orderId, String msg) {
        this.id = id;
        this.orderId = orderId;
        this.msg = msg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}