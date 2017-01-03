package com.securitysocial.spring.springsecuritysocial.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.securitysocial.spring.springsecuritysocial.model.User;
import com.securitysocial.spring.springsecuritysocial.model.UserDetailsSocialUser;
import com.securitysocial.spring.springsecuritysocial.repository.UserRepository;

public class RepositoryUserDetailsService implements UserDetailsService{

	private static final Logger logger = Logger.getLogger(RepositoryUserDetailsService.class);
	
	private UserRepository userRepository;
	
	@Autowired
	public RepositoryUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Loading user by username: {}"+ username);
		
		User user = userRepository.findByEmail(username);
		
		logger.debug("Found user: "+user);
		
		if(user==null){
			throw new UsernameNotFoundException("No user found with username: "+username);
		}
		UserDetailsSocialUser principal = UserDetailsSocialUser.getBuilder()
				.firstName(user.getFirstName())
				.id(user.getId())
				.lastName(user.getLastName())
				.password(user.getPassword())
				.role(user.getRole())
				.socialServiceProvider(user.getSocialServiceProvider())
				.username(user.getEmail())
				.build();
		
		logger.debug("Returning user details instance: "+ principal);
		
		return principal;
	}

}
