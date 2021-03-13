package com.sanbill.rocketmq.order.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private Long id;
    private Long orderId;
    private String msg;
}