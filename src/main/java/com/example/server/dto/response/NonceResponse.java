package com.example.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Nonce 응답")
public record NonceResponse(
	@Schema(description = "랜덤 Nonce 값", example = "ZapLovI7") String nonce) {
}
