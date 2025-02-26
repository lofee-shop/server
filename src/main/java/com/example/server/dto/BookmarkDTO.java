package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BookmarkDTO {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BookmarkRequest {
		private Long userId;      // 로그인한 사용자
		private Long productId;   // 북마크할 상품 ID
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BookmarkResponse {
		private String message;   // 결과 메시지
		private boolean bookmarked; // true = 북마크 성공
	}
}
