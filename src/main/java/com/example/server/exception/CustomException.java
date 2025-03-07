package com.example.server.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
