package com.example.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Nonce 응답")
public class NonceResponse {
	@Schema(description = "랜덤 Nonce 값", example = "123456")
	private String nonce;

	public NonceResponse(String nonce) {
		this.nonce = nonce;
	}

	public String getNonce() {
		return nonce;
	}
}
