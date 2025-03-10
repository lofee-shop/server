package com.example.server.common.response;

import com.example.server.common.exception.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
	private final int status;
	private final String message;
	private final T data;

	public static <T> ResponseDto<T> success(ResponseCode responseCode, T data) {
		return new ResponseDto<>(responseCode.getStatus(), responseCode.getMessage(), data);
	}

	public static ResponseDto<Void> success(ResponseCode responseCode) {
		return new ResponseDto<>(responseCode.getStatus(), responseCode.getMessage(), null);
	}

	public static <T> ResponseDto<T> fail(ResponseCode responseCode) {
		return new ResponseDto<>(responseCode.getStatus(), responseCode.getMessage(), null);
	}
}
