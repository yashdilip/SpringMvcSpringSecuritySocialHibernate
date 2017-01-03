package com.securitysocial.spring.springsecuritysocial.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.securitysocial.spring.springsecuritysocial.util.Role;
import com.securitysocial.spring.springsecuritysocial.util.SocialServiceProvider;

public class UserDetailsSocialUser extends SocialUser{
	
	private static final long serialVersionUID = 2858405085655351834L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private Role role;
	private SocialServiceProvider socialServiceProvider;
		
	public UserDetailsSocialUser(String username, String password, Collection<? extends GrantedAuthority> authorities){
		super(username, password, authorities);
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Role getRole() {
		return role;
	}

	public SocialServiceProvider getSocialServiceProvider() {
		return socialServiceProvider;
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(this)
				.append("id", id)
				.append("firstName", getFirstName())
				.append("lastName", getLastName())
				.append("role", getRole())
				.append("socialServiceProvider", getSocialServiceProvider())
				.toString();
	}
	
	public static class Builder{
		private Long id;
		private String username;
		private String password;
		private String firstName;
		private String lastName;
		private Role role;
		private SocialServiceProvider socialServiceProvider;
		private Set<GrantedAuthority> authorities;
		
		public Builder(){
			authorities = new HashSet<>();
		}
		
		public Builder id(Long id){
			this.id = id;
			return this;
		}
		public Builder firstName(String firstName){
			this.firstName = firstName;
			return this;
		}
		public Builder lastName(String lastName){
			this.lastName = lastName;
			return this;
		}
		public Builder username(String username){
			this.username = username;
			return this;
		}
		public Builder password(String password){
			if(password==null){
				password = "SocialUser";
			}
			this.password = password;
			return this;
		}
		public Builder role(Role role){
			this.role = role;
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
			this.authorities.add(authority);
			return this;
		}
		public Builder socialServiceProvider(SocialServiceProvider socialServiceProvider){
			this.socialServiceProvider = socialServiceProvider;
			return this;
		}
		
		public UserDetailsSocialUser build(){
			UserDetailsSocialUser user = new UserDetailsSocialUser(username, password, authorities);
			user.id = id;
			user.firstName = firstName;
			user.lastName = lastName;
			user.role = role;
			user.socialServiceProvider = socialServiceProvider;
			
			return user;
		}
	}
	
	public static Builder getBuilder(){
		return new Builder();
	}
}
