package com.green.house.login.service;

import com.green.house.login.dto.JWTResponse;
import com.green.house.login.dto.LoginRequest;
import com.green.house.login.dto.UserRequest;
import com.green.house.login.entity.Role;
import com.green.house.login.entity.User;
import com.green.house.login.entity.util.ERole;
import com.green.house.login.exception.ErrorCodes;
import com.green.house.login.exception.UserLoginException;
import com.green.house.login.repository.RoleRepository;
import com.green.house.login.repository.UserRepository;
import com.green.house.login.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {
	UserRepository userRepository;

	RoleRepository roleRepository;

	PasswordEncoder encoder;
	
	AuthenticationManager authenticationManager;
	
	JwtUtils jwtUtils;
	
	public LoginServiceImpl(UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder encoder,
			AuthenticationManager authenticationManager,
			JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@Override
	public boolean createUser(UserRequest userRequest) {

		if (userRepository.existsByEmail(userRequest.getEmail())) {
			throw new UserLoginException(ErrorCodes.USER_EXIST);
		}

		// Create new user's account
		User user = new User(userRequest.getEmail(), 
				encoder.encode(userRequest.getPassword()),
				userRequest.getMobile(),
				userRequest.getAddress(),
				userRequest.getDeviceId());

		Set<String> strRoles = userRequest.getRoles();

		user.setRoles(getRoles(strRoles));
		try {
			userRepository.save(user);
		} catch (Exception e) {
			throw new UserLoginException(e, ErrorCodes.USER_CREATION_ERROR);
		}

		return true;
	}

	private Set<Role> getRoles(Set<String> strRoles){
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
					.orElseThrow(() -> new UserLoginException(ErrorCodes.ROLE_NOT_FOUND));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new UserLoginException(ErrorCodes.ROLE_NOT_FOUND));
						roles.add(adminRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
								.orElseThrow(() -> new UserLoginException(ErrorCodes.ROLE_NOT_FOUND));
						roles.add(userRole);
				}
			});
		}
		return roles;
	}

	@Override
	public JWTResponse logInUser(LoginRequest loginRequest) {
		
		Authentication authentication = null;;
		try {
			authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch(AuthenticationException e) {
			throw new UserLoginException(e, ErrorCodes.INVALID_LOGIN);
		}
		

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return new JWTResponse(jwt, 
				 userDetails.getEmail(), 
				 roles);
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public JWTResponse lock(LoginRequest loginRequest) {
		Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new JWTResponse(null, user.get().getEmail(),null);
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public JWTResponse read(LoginRequest loginRequest) {
		Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
		User userdb = user.get();
		userdb.setDeviceId("xsfdsfsfsfsdf");
		userRepository.save(userdb);

		return new JWTResponse(null, user.get().getEmail(),null);
	}
}
