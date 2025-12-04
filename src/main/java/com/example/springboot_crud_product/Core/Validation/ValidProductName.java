package com.example.springboot_crud_product.Core.Validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ProductNameValidator.class)
public @interface ValidProductName {
	String message() default "Invalid product name";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}