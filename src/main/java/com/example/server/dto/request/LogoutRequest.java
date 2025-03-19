package com.example.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그아웃 요청 양식")
public record LogoutRequest(@Schema(description = "지갑 주소", required = true) String walletAddress) {
}
