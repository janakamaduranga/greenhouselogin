package com.green.house.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8524251833934454224L;
	private String message;
}
