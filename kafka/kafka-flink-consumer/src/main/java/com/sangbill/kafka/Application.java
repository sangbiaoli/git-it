package com.sangbill.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sangbill.kafka.flink.consumer.ReadFromKafka;
import com.sangbill.kafka.util.SpringUtil;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
        System.out.println("=============kafka-flink-consumer!=============");	
        
        ReadFromKafka readFromKafka = SpringUtil.getBean(ReadFromKafka.class);
        try {
			readFromKafka.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}