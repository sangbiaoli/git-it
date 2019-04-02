package com.sangbill.kafka.flink.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.sangbill.kafka.util.LangUtils;

@Component
@EnableScheduling
public class FlinkInputProducer {
	
	@Value("${kafka.producer.topic.flinkInput}")
	private String topFlinkInput;
	
	@Autowired
	private KafkaTemplate<?, String> kafkaTemplate;

	@Scheduled(cron="0/5 * * * * ?")
	public void send() {
		String message = LangUtils.randomWords();
		ListenableFuture<?> future = kafkaTemplate.send(topFlinkInput, message);
		future.addCallback(
			o -> System.out.println("send-消息发送成功：" + message),
			throwable -> System.out.println("消息发送失败：" + message)
		);
	}

}