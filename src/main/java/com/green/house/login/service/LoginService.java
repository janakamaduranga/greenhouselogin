package com.green.house.login.service;

import com.green.house.login.dto.JWTResponse;
import com.green.house.login.dto.LoginRequest;
import com.green.house.login.dto.UserRequest;

public interface LoginService {
	public boolean createUser(UserRequest loginRequest);
	public JWTResponse logInUser(LoginRequest loginRequest);

	public JWTResponse lock(LoginRequest loginRequest);
	public JWTResponse read(LoginRequest loginRequest);
}
