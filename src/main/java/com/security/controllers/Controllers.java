package com.security.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.AuthReq;
import com.security.entities.UserEntity;
import com.security.services.UserService;

@RestController
public class Controllers {
	private final UserService userService;

	public Controllers(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/add/user")
	public String addUser(@RequestBody UserEntity entity) {
		return userService.addUser(entity);
	}
	
	@PostMapping("/authenticate")
	public  Map<String, Object> authenticate(@RequestBody AuthReq req) {
		return userService.authenticate(req);
	}
}
