package com.security.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.entities.UserEntity;
import com.security.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepo userRepo;
	
	public CustomUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userRepo.findByUsername(username);
		
		return user.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
