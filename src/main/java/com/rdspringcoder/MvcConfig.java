/**
 * 
 */
package com.rdspringcoder;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * <p>
 * </p>
 * 
 * @author Ritesh Dharmatti
 * 
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public /*DriverManagerDataSource*/DataSource dataSource(){
//		DriverManagerDataSource dmds = new DriverManagerDataSource();
//		dmds.setDriverClassName("com.mysql.jdbc.Driver");
//		dmds.setUrl("jdbc:mysql://localhost:3306/cars");
//		dmds.setUsername("root");
//		dmds.setPassword("root");
//		return dmds;
		return DataSourceBuilder.create().build();
	}
}
