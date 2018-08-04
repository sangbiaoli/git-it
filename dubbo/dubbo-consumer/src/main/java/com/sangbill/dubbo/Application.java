package com.sangbill.dubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sangbill.dubbo.service.DubboConsumerService;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        DubboConsumerService consumerService = run.getBean(DubboConsumerService.class);
        consumerService.printMsg();
    }
}