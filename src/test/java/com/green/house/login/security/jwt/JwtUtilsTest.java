package com.green.house.login.security.jwt;

import com.green.house.login.entity.util.ERole;
import com.green.house.login.service.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class JwtUtilsTest {
	JwtUtils jwtUtils = new JwtUtils("Test", 36000);
	
	@Test
	void generateJwtToken() {
		final String EMAIL = "test@gg.com";
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name());
		authorities.add(adminAuthority);
		UserDetailsImpl userDetails = new UserDetailsImpl(200L, EMAIL, "12", authorities,"arduino");
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(userDetails, adminAuthority);
		String token = jwtUtils.generateJwtToken(authentication);
		assertEquals(EMAIL, jwtUtils.getEmail(token));
		assertEquals(ERole.ROLE_ADMIN.name(), jwtUtils.getRoles(token).get(0));
	}

}
