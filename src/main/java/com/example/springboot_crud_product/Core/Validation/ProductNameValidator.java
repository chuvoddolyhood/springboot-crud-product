package com.example.springboot_crud_product.Core.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductNameValidator implements ConstraintValidator<ValidProductName, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isBlank())
			return false;
		// Không chứa ký tự đặc biệt
		return value.matches("^[a-zA-Z0-9 ]+$");
	}
}
