package com.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);
	
}
