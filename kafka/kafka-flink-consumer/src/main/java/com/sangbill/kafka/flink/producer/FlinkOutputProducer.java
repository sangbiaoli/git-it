package com.sangbill.kafka.flink.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@EnableScheduling
public class FlinkOutputProducer {
	
	@Value("${kafka.producer.topic.flinkOutput}")
	private String topFlinkOutput;
	
	@Autowired
	private KafkaTemplate<?, String> kafkaTemplate;

	public void send(String message) {
		ListenableFuture<?> future = kafkaTemplate.send(topFlinkOutput, message);
		future.addCallback(
			o -> System.out.println("send-消息发送成功：" + message),
			throwable -> System.out.println("消息发送失败：" + message)
		);
	}

}