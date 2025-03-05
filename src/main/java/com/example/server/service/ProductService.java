package com.example.server.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.dto.response.ProductDetailResponse;
import com.example.server.entity.Product;
import com.example.server.entity.ProductThumbnail;
import com.example.server.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	// 북마크 여부 체크를 원한다면 아래 활성화
	// private final BookmarkRepository bookmarkRepository;

	@Transactional(readOnly = true)
	public ProductDetailResponse getProductDetail(Long productId, Long userId) {
		// 1) product 조회 (없으면 NoSuchElementException → 404)
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new NoSuchElementException("Product not found"));

		// 2) 판매자 정보
		String sellerNickname = product.getUser().getNickname();
		String sellerWallet = product.getUser().getWalletAddress();

		// 3) 이미지 목록 (ProductThumbnail)
		List<String> imageUrls = product.getProductThumbnails().stream()
			.map(ProductThumbnail::getImgPath)
			.collect(Collectors.toList());

		// 4) 북마크 여부 (선택)
		// Boolean isBookmarked = null;
		// if (userId != null) {
		//     // DB에서 userId + productId 북마크 존재 여부 확인
		//     // isBookmarked = bookmarkRepository.existsByUserIdAndProductId(userId, productId);
		// }

		// 5) DTO 변환 (price는 BigDecimal 그대로 반환)
		return new ProductDetailResponse(
			product.getId(),
			product.getProductName(),
			product.getPrice(),       // BigDecimal
			product.getInfo(),        // description
			sellerNickname,
			sellerWallet,
			imageUrls,
			null // isBookmarked (주석 해제 시 isBookmarked)
		);
	}
}
