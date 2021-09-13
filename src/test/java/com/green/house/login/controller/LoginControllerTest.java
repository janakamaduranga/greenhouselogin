package com.green.house.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.house.login.dto.LoginRequest;
import com.green.house.login.dto.UserRequest;
import com.green.house.login.security.WebSecurityConfig;
import com.green.house.login.service.LoginService;
import com.green.house.login.service.UserDetailsServiceImpl;
import com.green.house.login.security.jwt.AuthEntryPointJwt;
import com.green.house.login.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import({WebSecurityConfig.class})
@WebMvcTest(controllers=LoginController.class)
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private LoginService loginService;
	
	@MockBean
	private JwtUtils JwtUtils;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@MockBean
	AuthEntryPointJwt unauthorizedHandler;
	
	
	@BeforeEach
	public void setUp() {
		when(loginService.createUser(any(UserRequest.class)))
		.thenReturn(true);
	}
	
//	@Test
//	 void registerUser_When_Valid_Input_Then_Success() throws JsonProcessingException, Exception {
//		final String email = "ja@aa.com";
//		final String pwd = "234";
//		final Set<String> roles = new HashSet<String>(Arrays.asList("admin"));
//		LoginRequest loginReqeust = new LoginRequest(email, pwd, roles);
//
//		String result = mockMvc.perform(post("/signup")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(objectMapper.writeValueAsString(loginReqeust)))
//				.andExpect(status().isOk())
//				.andReturn().getResponse().getContentAsString();
//		assertEquals("User created successfully", result);
//
//
//	}
//
//	@Test
//	 void registerUser_When_Valid_Input_Then_Fail() throws JsonProcessingException, Exception {
//		final String email = "ja@aa.com";
//		final String pwd = "234";
//		final Set<String> roles = new HashSet<String>(Arrays.asList("admin"));
//		LoginRequest loginReqeust = new LoginRequest(email, pwd, roles);
//		when(loginService.createUser(any(UserRequest.class)))
//		.thenReturn(false);
//
//		mockMvc.perform(post("/signup")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(objectMapper.writeValueAsString(loginReqeust)))
//				.andExpect(status().isFailedDependency());
//	}
}
