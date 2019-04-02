package com.sangbill.kafka.service;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
 
	/**
	 * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)
	 * @param record
	 * @throws IOException
	 */
	@KafkaListener(topics = "test")
    public void receive(String content){
        System.err.println("Receive:" + content);
    }

}