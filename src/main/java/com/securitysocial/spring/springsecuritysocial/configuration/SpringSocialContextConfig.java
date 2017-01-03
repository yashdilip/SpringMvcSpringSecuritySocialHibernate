package com.securitysocial.spring.springsecuritysocial.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
@EnableSocial
@Profile("application")
public class SpringSocialContextConfig implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connection, Environment env) {
		connection.addConnectionFactory(new TwitterConnectionFactory(
				env.getProperty("twitter.consumer.key"),
				env.getProperty("twitter.consumer.secret")
				));
		connection.addConnectionFactory(new FacebookConnectionFactory(
				env.getProperty("facebook.app.id"),
				env.getProperty("facebook.app.secret")
				));
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new JdbcUsersConnectionRepository(
				dataSource,
				connectionFactoryLocator,
				Encryptors.noOpText()
				);
	}
	
	@Bean
	public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository){
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}

}
