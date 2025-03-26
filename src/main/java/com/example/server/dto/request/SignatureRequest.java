package com.example.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 양식")
public record SignatureRequest(
	@Schema(description = "메시지 내용", required = true) String message,
	@Schema(description = "서명 내용", example = "0xabcdef1234567890...", required = true) String signature) {
}