package com.securitysocial.spring.springsecuritysocial.service;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SimpleSocialUserDetailsService implements SocialUserDetailsService{

	private static final Logger logger = Logger.getLogger(SimpleSocialUserDetailsService.class);
	
	private UserDetailsService userDetailsService;
	
	public SimpleSocialUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Loading user by userId: "+ userId);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
		
		logger.debug("Found user details: "+ userDetails);
		
		return (SocialUserDetails) userDetails;
	}

}
