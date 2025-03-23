package com.example.server.dto.response;

import java.util.List;

public record ProductDetailResponse(
	Long productId,
	String productName,
	Double price,
	String Info,
	String sellerName,
	List<String> imageUrls,
	boolean isBookmarked
) {
}
