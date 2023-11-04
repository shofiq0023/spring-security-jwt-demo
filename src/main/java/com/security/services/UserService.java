package com.security.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dto.AuthReq;
import com.security.entities.UserEntity;
import com.security.repo.UserRepo;
import com.security.utils.JwtUtils;

@Service
public class UserService {
	private final UserRepo userRepo;
	private final PasswordEncoder passEncoder;
	private final AuthenticationManager authManager;
	private final JwtUtils jwtUtils;

	public UserService(UserRepo userRepo, PasswordEncoder passEncoder, AuthenticationManager authManager,
			JwtUtils jwtUtils) {
		this.userRepo = userRepo;
		this.passEncoder = passEncoder;
		this.authManager = authManager;
		this.jwtUtils = jwtUtils;
	}

	public String addUser(UserEntity entity) {
		entity.setPassword(passEncoder.encode(entity.getPassword()));
		userRepo.save(entity);
		
		return "User created";
	}

	public Map<String, Object> authenticate(AuthReq req) {
		Optional<UserEntity> user = userRepo.findByUsername(req.getUsername());
		Map<String, Object> claims = new HashMap<String, Object>();
		
		if (user.isPresent()) {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		}
		
		claims.put("id", user.get().getId());
		claims.put("username", user.get().getUsername());
		claims.put("email", user.get().getEmail());
		
		String token = jwtUtils.generateToken(claims, user.get().getUsername());
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("Token", token);
		
		return output;
	}

}
