package com.securitysocial.spring.springsecuritysocial.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.securitysocial.spring.springsecuritysocial.util.Role;
import com.securitysocial.spring.springsecuritysocial.util.SocialServiceProvider;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;
	
	@Column(name = "password", length = 255)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 20, nullable = false)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "service_provider", length = 20)
	private SocialServiceProvider socialServiceProvider;
	
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(name = "creation_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationTime;
	
	@Column(name = "modification_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime modificationTime;
	
	@Version
	private long version;
	
	public User(){ }

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public SocialServiceProvider getSocialServiceProvider() {
		return socialServiceProvider;
	}

	public String getEmail() {
		return email;
	}
	
	public DateTime getCreationTime() {
		return creationTime;
	}

	public DateTime getModificationTime() {
		return modificationTime;
	}

	public long getVersion() {
		return version;
	}

	@PrePersist
	public void prePresist(){
		DateTime now = DateTime.now();
		this.creationTime = now;
		this.modificationTime = now;
	}
	
	@PreUpdate
	public void preUpdate() {
		this.modificationTime = DateTime.now();
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(this)
				.append("id", id)
				.append("email", email)
				.append("firstName", firstName)
				.append("lastName", lastName)
				.append("socialServiceProvider", socialServiceProvider)
				.append("creationTime", creationTime)
				.append("modificationTime", modificationTime)
				.append("version", version)
				.toString();
	}
	
	public static class Builder {
		private User user;
		
		public Builder(){
			user = new User();
			user.role = Role.ROLE_USER;
		}
		
		public Builder email(String email){
			user.email = email;
			return this;
		}
		public Builder firstName(String firstName){
			user.firstName = firstName;
			return this;
		}
		public Builder lastName(String lastName){
			user.lastName = lastName;
			return this;
		}
		public Builder password(String password){
			user.password = password;
			return this;
		}
		public Builder socialServiceProvider(SocialServiceProvider socialServiceProvider){
			user.socialServiceProvider = socialServiceProvider;
			return this;
		}
		
		public User build(){
			return user;
		}
	}
	
	public static Builder getBuilder(){
		return new Builder();
	}
}
