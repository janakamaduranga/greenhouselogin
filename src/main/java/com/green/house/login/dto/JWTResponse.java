package com.green.house.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2355297075135429455L;
	
	private String token;
	private String email;
	private List<String> roles;
}
