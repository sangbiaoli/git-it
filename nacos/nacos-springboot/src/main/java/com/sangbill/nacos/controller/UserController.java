package com.sangbill.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
class UserController {

	@Value("${user.name}")
	private String userName;

	@Value("${user.age}")
	private int age;

	@RequestMapping("/user")
	public String getUser() {
		return userName + " " + age;
	}
}