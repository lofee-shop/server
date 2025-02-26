package com.example.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRequest {
	private Long userId;      // 로그인한 사용자
	private Long productId;   // 북마크할 상품 ID
}
