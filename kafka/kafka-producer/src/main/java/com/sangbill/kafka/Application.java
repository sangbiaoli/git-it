package com.sangbill.kafka;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sangbill.kafka.service.KafkaProducer;
import com.sangbill.kafka.util.SpringUtil;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
        System.out.println("启动kafka-consumer成功");
        sendMsg();
		
	}

	private static void sendMsg() {
		KafkaProducer producer = SpringUtil.getBean(KafkaProducer.class);
		for (;;) {
			try {
				producer.send();
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}