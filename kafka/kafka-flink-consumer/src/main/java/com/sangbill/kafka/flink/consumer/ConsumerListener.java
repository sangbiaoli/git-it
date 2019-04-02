package com.sangbill.kafka.flink.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class ConsumerListener {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@KafkaListener(topics = { "${kafka.consumer.topic.flinkInput}" })
	public void handleFlinkInput(ConsumerRecord<?, ?> record) {
		String value = record.value().toString();
		logger.info("rev flinkInput data: " + value);
	}
}