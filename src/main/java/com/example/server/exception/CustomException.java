package com.example.server.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ResponseCode responseCode;

	public CustomException(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
}
