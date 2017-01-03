package com.securitysocial.spring.springsecuritysocial.configuration;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableJpaRepositories(basePackages={"com.securitysocial.spring.springsecuritysocial.repository"})
@EnableTransactionManagement
public class PersistenceContextConfig {

	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource(){
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(env.getProperty("spring.database.driverClassName"));
		dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.securitysocial.spring.springsecuritysocial.model");
		
		Properties jpaProperties = new Properties();
		
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy"));
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		
		return entityManagerFactoryBean;
		
		
	}
	
	@Bean
	public JpaTransactionManager transactionManager(){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
