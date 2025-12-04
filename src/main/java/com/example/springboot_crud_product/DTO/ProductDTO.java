package com.example.springboot_crud_product.DTO;

import com.example.springboot_crud_product.Core.Validation.ValidProductName;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductDTO {
	private Long id;

	@NotBlank(message = "Name cannot be empty")
	@ValidProductName
	private String name;

	@Min(value = 0, message = "Price must be positive")
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be positive")
	private Double price;

}
