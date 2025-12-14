package com.example.springboot_crud_product.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springboot_crud_product.Security.JwtFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
    JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		System.out.println(">>> LOADED SecurityConfig CONFIG <<<");

	    http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
//	                    .requestMatchers("/auth/public/**").permitAll()
//	                    .requestMatchers("/auth/secure").authenticated()
//	                    .requestMatchers("/auth/admin/**").hasRole("ADMIN")
//	                    .requestMatchers("/auth/user/**").hasAnyRole("ADMIN", "USER")
	                    .requestMatchers("/auth/**").permitAll()
	                    .anyRequest().authenticated())
	            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//	            .httpBasic(Customizer.withDefaults());

	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	

}