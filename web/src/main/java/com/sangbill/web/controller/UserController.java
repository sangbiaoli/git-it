package com.sangbill.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

	@GetMapping("/getUser")
	public String getUser() {
		return "getUser";
	}

	@PostMapping("/addUser")
	public String addUser(@RequestBody String body) {
		System.out.println(body);
		return "addUser";
	}
}
