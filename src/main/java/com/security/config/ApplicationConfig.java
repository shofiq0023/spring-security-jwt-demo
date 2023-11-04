package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
	private final CustomUserDetailsService customUserDetailsService;

	public ApplicationConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(customUserDetailsService);
		
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
