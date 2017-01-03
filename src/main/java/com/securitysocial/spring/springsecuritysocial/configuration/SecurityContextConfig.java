package com.securitysocial.spring.springsecuritysocial.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.securitysocial.spring.springsecuritysocial.repository.UserRepository;
import com.securitysocial.spring.springsecuritysocial.service.RepositoryUserDetailsService;
import com.securitysocial.spring.springsecuritysocial.service.SimpleSocialUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityContextConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//Spring security ignoring request to static resources
		web
		.ignoring()
		.antMatchers("/static/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()      //configures form login
		.loginPage("/login")
		.loginProcessingUrl("/login/authenticate")
		.failureUrl("/login?error=bad_credentials")
		.and()
		.logout()//configures logout function
		.deleteCookies("JSESSIONID")
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login")
		.and() //configures url based authorization
		.authorizeRequests()
		.antMatchers( //anyone can access the urls
				"/auth/**",
				"/login",
				"/signin/**",
				"/signup/**",
				"/user/register/**"
				).permitAll() //the rest of urls are protected
		.antMatchers("/**").hasRole("USER")
		.and() //adds SocialAuthenticationFilter to spring security filter chain
		.apply(new SpringSocialConfigurer())
		.and()
		.setSharedObject(ApplicationContext.class, context);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService())
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public SocialUserDetailsService socialUserDetailsService(){
		return new SimpleSocialUserDetailsService(userDetailsService());
	}
	
	@Bean
	public UserDetailsService userDetailsService(){
		return new RepositoryUserDetailsService(userRepository);
	}
}
