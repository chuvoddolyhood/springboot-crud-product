package com.example.springboot_crud_product.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@ComponentScan
public class UserConfig {
	
	@Autowired
	SecurityConfig securityConfig;

	@Bean
	public UserDetailsService userDetailService() {
		System.out.println(">>> LOADED userDetailService CONFIG <<<");
		
		UserDetails admin = User.withUsername("admin")
				.password(securityConfig.passwordEncoder().encode("123"))
				.roles("ADMIN").build();
		
		UserDetails user = User.withUsername("user")
		        .password(securityConfig.passwordEncoder().encode("456"))
		        .roles("USER")
		        .build();

		
		return new InMemoryUserDetailsManager(admin, user);
	}
	

}