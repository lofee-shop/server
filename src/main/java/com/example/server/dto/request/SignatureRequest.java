package com.example.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "로그인 요청 양식")
public class SignatureRequest {
	@Schema(description = "메시지 내용", required = true)
	private String message;
	@Schema(description = "서명 내용", example = "0xabcdef1234567890...", required = true)
	private String signature;
}