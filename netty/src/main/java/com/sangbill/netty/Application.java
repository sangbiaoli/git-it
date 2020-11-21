package com.sangbill.netty;

import com.sangbill.netty.base.PortalContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.addListeners(new PortalContextListener());
		application.run(args);
	}
}