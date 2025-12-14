package com.example.springboot_crud_product.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_crud_product.DTO.AuthResponse;
import com.example.springboot_crud_product.DTO.LoginRequest;
import com.example.springboot_crud_product.DTO.RegisterRequest;
import com.example.springboot_crud_product.Service.Interface.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;

	@GetMapping("/public")
	public String authPublic() {
		return "Hello public authentication";
	}
		
	@GetMapping("/secure")
	public String secureUser() {
		return "Hello secure User authentication";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard() {
		return "Hello adminDashboard";
	}
	
	@GetMapping("/user/profile")
	public String userProfile() {
		return "Hello userProfile";
	}
	
	/**
	 * http://localhost:9090/auth/login
	 * 
	 * */
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest req) {
		return authService.login(req);
	}
	
	/**
	 * http://localhost:9090/auth/register/user
	 * 
	 * */
	@PostMapping("/register/{role}")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req, @PathVariable("role") String role) {
		authService.register(req, role);
		return ResponseEntity.ok("Register successful");
	}

}
