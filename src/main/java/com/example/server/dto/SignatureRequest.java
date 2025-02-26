package com.example.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "로그인 요청 양식")
public class SignatureRequest {
	@Schema(description = "지갑 주소", example = "0x123456789abcdef", required = true)
	private String walletAddress;
	@Schema(description = "백엔드에서 전송받은 nonce 값", example = "123456", required = true)
	private String nonce;
	@Schema(description = "지갑주소,nonce,... 을 포함한 서명 내용", example = "0xabcdef1234567890...", required = true)
	private String signature;
}