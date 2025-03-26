package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.response.ProductDetailResponse;
import com.example.server.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "상품 상세 정보 조회", description = "상품 ID로 상품의 상세 정보를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "상품 상세 정보 조회 성공"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 상품")
	})
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.getProductDetail(productId));
	}
}
