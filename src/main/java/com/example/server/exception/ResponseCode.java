package com.example.server.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {
	// 사용자 관련 오류
	USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

	// 주소 관련 오류
	ADDRESS_NOT_FOUND(404, "배송지를 찾을 수 없습니다."),

	// 파일 업로드 오류
	FILE_UPLOAD_FAILED(400, "파일 업로드에 실패하였습니다."),

	//지갑 주소 유효성 오류
	WALLET_NOT_FOUND(401, "지갑 주소가 유효하지 않습니다"),

	//Nonce 유효성 오류
	NONCE_ERR(402, "Nonce 값이 유효하지 않습니다"),

	//지갑 주소 복원 실패 오류
	RECOVER_ADDRESS_FAILED(501, "이더리움 지갑 주소 복원에 실패하였습니다");

	private final int status;
	private final String message;

	ResponseCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
