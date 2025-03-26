package com.example.server.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {
	// 사용자 관련 오류
	USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

	// 주소 관련 오류
	ADDRESS_NOT_FOUND(404, "배송지를 찾을 수 없습니다."),

	ADDRESS_LIMIT_EXCEEDED(400, "배송지는 최대 5개까지만 등록할 수 있습니다."),

	// 파일 업로드 오류
	FILE_UPLOAD_FAILED(400, "파일 업로드에 실패하였습니다."),

	// 이미지 타입 오류
	INVALID_IMAGE_TYPE(400, "잘못된 이미지 타입입니다.");

	private final int status;
	private final String message;

	ResponseCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
