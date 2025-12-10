package com.example.springboot_crud_product.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		System.out.println(">>> LOADED SecurityConfig CONFIG <<<");

	    http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers("/auth/public/**").permitAll()
	                    .requestMatchers("/auth/secure").authenticated()
	                    .requestMatchers("/auth/admin/**").hasRole("ADMIN")
	                    .requestMatchers("/auth/user/**").hasAnyRole("ADMIN", "USER")
	                    .anyRequest().authenticated())
	            .httpBasic(Customizer.withDefaults());

	    return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	

}