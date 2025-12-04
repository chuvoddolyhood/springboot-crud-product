package com.example.springboot_crud_product.Core.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomErrorResponse {
	private int status;
	private String error;
	private String message;
	private String path;
	private String timestamp;
}
