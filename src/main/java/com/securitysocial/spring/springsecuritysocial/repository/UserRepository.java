package com.securitysocial.spring.springsecuritysocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securitysocial.spring.springsecuritysocial.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByEmail(String email);
}
