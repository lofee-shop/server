package com.example.server.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
	private Long productId;
	private String productName;
	private BigDecimal price;
	private String description;
	private String sellerNickname;
	private String sellerWalletAddress;
	private List<String> images; // 썸네일 URL 목록
	private Boolean isBookmarked; // 선택. 로그인 사용자 있을 시 북마크 여부
}
