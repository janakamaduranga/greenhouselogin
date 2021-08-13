package com.green.house.login.controller;

import com.green.house.login.dto.JWTResponse;
import com.green.house.login.dto.LoginRequest;
import com.green.house.login.dto.UserRequest;
import com.green.house.login.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge=3600)
public class LoginController {
	
	LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@PostMapping(path="/signIn")
	public ResponseEntity<JWTResponse> logInUser(@RequestBody LoginRequest loginRequest){

		return ResponseEntity.ok(loginService.logInUser(loginRequest));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody UserRequest loginRequest) {
		if(loginService.createUser(loginRequest)) {
			return ResponseEntity.ok("User created successfully");
		} else {
			return new ResponseEntity<>("User creation failure", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
			return ResponseEntity.ok("login test success");
		
	}

}
