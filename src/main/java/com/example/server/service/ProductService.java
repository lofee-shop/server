package com.example.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.dto.response.ProductDetailResponse;
import com.example.server.entity.Product;
import com.example.server.entity.ProductImage;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.repository.ProductRepository;
import com.example.server.repository.WatchlistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;
	private final WatchlistRepository bookmarkRepository;

	public ProductDetailResponse getProductDetail(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ResponseCode.PRODUCT_NOT_FOUND));

		List<String> imageUrls = product.getImages().stream()
			.map(ProductImage::getImgPath)
			.collect(Collectors.toList());

		boolean isBookmarked = bookmarkRepository.existsByProductId(productId);

		return new ProductDetailResponse(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getInfo(),
			product.getSeller().getName(),
			imageUrls,
			isBookmarked
		);
	}
}
