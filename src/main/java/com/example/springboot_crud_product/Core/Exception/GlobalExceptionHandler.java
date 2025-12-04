package com.example.springboot_crud_product.Core.Exception;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// ------------------- Resource Not Found 404-------------------
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<CustomErrorResponse> handleNotFound(RuntimeException ex, HttpServletRequest request) {
		log.error("Resource not found: {}", ex.getMessage());

		Instant nowUtc = Instant.now();
		String utcString = DateTimeFormatter.ISO_INSTANT.format(nowUtc);

		CustomErrorResponse error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getRequestURI(), utcString);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	// ------------------- Bad Request 400-------------------
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CustomErrorResponse> handleBadRequest(IllegalArgumentException ex,
			HttpServletRequest request) {
		log.error("Bad request: {}", ex.getMessage());
		CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI(),
				java.time.OffsetDateTime.now().toString());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	// ------------------- Global Exception 500-------------------
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorResponse> handleAll(Exception ex, HttpServletRequest request) {
		log.error("Internal server error: ", ex);
		CustomErrorResponse error = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Internal server error", request.getRequestURI(),
				java.time.OffsetDateTime.now().toString());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	// ------------------- Validation Exception -------------------
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		String errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining("; "));

		log.warn("Validation error: {}", errors);

		CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), errors, request.getRequestURI(),
				OffsetDateTime.now().toString());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
