package com.example.springboot_crud_product.DTO;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String accessToken;
    private String refreshToken;

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
