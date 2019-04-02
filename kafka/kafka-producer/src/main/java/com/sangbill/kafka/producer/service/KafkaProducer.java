package com.sangbill.kafka.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.sangbill.kafka.util.LangUtils;

@Component
@EnableScheduling
public class KafkaProducer {
	private final String TOPIC_NAME = "test";

	@Autowired
	private KafkaTemplate<?, String> kafkaTemplate;

	public void send() {
		String message = LangUtils.randomWords();
		ListenableFuture<?> future = kafkaTemplate.send(TOPIC_NAME, message);
		future.addCallback(
			o -> System.out.println("send-消息发送成功：" + message),
			throwable -> System.out.println("消息发送失败：" + message)
		);
	}

}