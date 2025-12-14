package com.example.springboot_crud_product.Service.Interface;

import com.example.springboot_crud_product.DTO.AuthResponse;
import com.example.springboot_crud_product.DTO.LoginRequest;
import com.example.springboot_crud_product.DTO.RegisterRequest;

public interface AuthService {

	public AuthResponse login(LoginRequest req);
	
	public void register(RegisterRequest req, String role);

}
