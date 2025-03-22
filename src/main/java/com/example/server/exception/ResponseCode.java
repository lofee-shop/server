package com.example.server.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {
	// 사용자 관련 오류
	USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

	// 상품 (장바구니) 관련 오류
	PRODUCT_NOT_FOUND(404, "상품을 찾을 수 없습니다."),
	INVALID_REQUEST(400, "요청이 올바르지 않습니다."),
	FORBIDDEN(403, "로그인이 필요합니다."),
	STOCK_NOT_ENOUGH(409, "재고가 부족합니다."),

	// 상품 (북마크) 관련 오류
	ALREADY_BOOKMARKED(409, "이미 북마크한 상품입니다."),

	// 주소 관련 오류
	ADDRESS_NOT_FOUND(404, "배송지를 찾을 수 없습니다."),

	// 파일 업로드 오류
	FILE_UPLOAD_FAILED(400, "파일 업로드에 실패하였습니다.");

	private final int status;
	private final String message;

	ResponseCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
