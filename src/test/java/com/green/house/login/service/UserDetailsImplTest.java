package com.green.house.login.service;

import com.green.house.login.entity.Role;
import com.green.house.login.entity.User;
import com.green.house.login.entity.util.ERole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserDetailsImplTest {

	@Test
	void buildUserDetailsImpl() {
		final String EMAIL = "test@abc.com";
		User user = new User(EMAIL, "123","23244","address");
		Role adminRole = new Role(ERole.ROLE_ADMIN);
		user.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));
		
		UserDetailsImpl userDetails = UserDetailsImpl.build(user);
		assertEquals(EMAIL, userDetails.getEmail());
		assertEquals(ERole.ROLE_ADMIN.name(),
				((SimpleGrantedAuthority)((List<GrantedAuthority>)userDetails.getAuthorities()).get(0))
				.getAuthority());
	}
}
