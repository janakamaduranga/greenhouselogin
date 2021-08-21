package com.green.house.login.controller;

import com.green.house.login.dto.JWTResponse;
import com.green.house.login.dto.LoginRequest;
import com.green.house.login.dto.UserRequest;
import com.green.house.login.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


@RestController
@CrossOrigin
public class LoginController {
	
	LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@PostMapping(path="/signIn")
	public ResponseEntity<JWTResponse> logInUser(@RequestBody LoginRequest loginRequest){

		return ResponseEntity.ok(loginService.logInUser(loginRequest));
	}
	
	@PostMapping(value = "/signup")
	public ResponseEntity<String> registerUser(@RequestBody UserRequest loginRequest) {
		if(loginService.createUser(loginRequest)) {
			return ResponseEntity.ok("User created successfully");
		} else {
			return new ResponseEntity<>("User creation failure", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@GetMapping(value = "/test", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> test() {
			return ResponseEntity.ok("login test success");
		
	}

}
