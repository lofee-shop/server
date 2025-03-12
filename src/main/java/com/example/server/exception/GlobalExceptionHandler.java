package com.example.server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> handleCustomException(CustomException exception) {
		return ResponseEntity.status(exception.getResponseCode().getStatus())
			.body(exception.getResponseCode().getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception exception) {
		return ResponseEntity.status(500).body(exception.getMessage());
	}
}
