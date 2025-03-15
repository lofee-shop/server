package com.example.server.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailResponse(
	Long productId,
	String productName,
	BigDecimal price,
	String description,
	String sellerNickname,
	String sellerWalletAddress,
	List<String> images,       // 썸네일 URL 목록
	Boolean isBookmarked       // 선택. 로그인 사용자 있을 시 북마크 여부
) {
}
