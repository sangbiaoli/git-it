package com.sangbill.kafka.flink.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.sangbill.kafka.flink.producer.FlinkOutputProducer;

public class ConsumerListener {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FlinkOutputProducer flinkOutputProducer;

	@KafkaListener(topics = { "${kafka.consumer.topic.flinkInput}" })
	public void handleFlinkInput(ConsumerRecord<?, ?> record) {
		String value = record.value().toString();
		logger.info("rev flinkInput data: " + value);
		flinkOutputProducer.send("output " + value);
	}
}