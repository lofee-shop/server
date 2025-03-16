package com.example.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT 인증 응답")
public record AuthResponse(
	@Schema(description = "JWT accessToken", example = "eyJhbGciOiJIUzI1NiIsIn...") String accessToken,
	@Schema(description = "JWT refreshToken", example = "eyJhbGciOiJIUzI1NiIsIn...") String refreshToken) {
}