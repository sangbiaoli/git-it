package com.sangbill.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

	@GetMapping("/getUser")
	public String getUser() {
		return "getUser";
	}

	@PostMapping("/addUser")
	public String addUser() {
		return "addUser";
	}
}