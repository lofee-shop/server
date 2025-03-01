package com.example.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "JWT 인증 응답")
public class AuthResponse {
	@Schema(description = "검증 성공 여부", example = "true")
	private boolean isVerified;

	@Schema(description = "JWT accessToken", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String accessToken;

	@Schema(description = "JWT refreshToken", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String refreshToken;

	public AuthResponse(String accessToken, String refreshToken, boolean isVerified) {
		this.isVerified = isVerified;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public AuthResponse(boolean isVerified) {
		this.isVerified = isVerified;
		this.accessToken = null;
		this.refreshToken = null;
	}
}