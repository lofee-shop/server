package com.example.server.common.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {
	// 성공 응답
	SUCCESS(200, "요청이 성공적으로 수행되었습니다."),
	CREATED(201, "업로드가 완료되었습니다."),

	// 사용자 관련 오류
	USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

	// 주소 관련 오류
	ADDRESS_NOT_FOUND(404, "배송지를 찾을 수 없습니다."),

	// 파일 업로드 오류
	FILE_UPLOAD_FAILED(400, "파일 업로드에 실패하였습니다."),

	// 잘못된 요청
	BAD_REQUEST(400, "잘못된 요청입니다."),

	// 서버 오류
	INTERNAL_SERVER_ERROR(500, "서버 오류가 발생했습니다.");

	private final int status;
	private final String message;

	ResponseCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
