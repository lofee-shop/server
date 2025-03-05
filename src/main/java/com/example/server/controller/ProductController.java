package com.example.server.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.response.ProductDetailResponse;
import com.example.server.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "상품 상세 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "요청 성공, 상품 정보를 반환함"),
		@ApiResponse(responseCode = "400", description = "productId가 숫자가 아닌 경우"),
		@ApiResponse(responseCode = "404", description = "해당 productId의 상품이 없는 경우")
	})
	@GetMapping("/products/{productId}")
	public ResponseEntity<?> getProductDetail(
		@PathVariable String productId,
		@RequestParam(required = false) Long userId // 선택: 북마크 여부 확인용
	) {
		// 1) productId가 숫자인지 확인
		long pid;
		try {
			pid = Long.parseLong(productId);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(createError("Bad Request", "Product ID must be a number."));
		}

		// 2) service 호출
		try {
			ProductDetailResponse detail = productService.getProductDetail(pid, userId);
			return ResponseEntity.status(200).body(detail);
		} catch (NoSuchElementException e) {
			// Product not found
			return ResponseEntity.status(404).body(createError("Not Found", e.getMessage()));
		}
	}

	private Object createError(String error, String message) {
		return new Object() {
			public String getError() {
				return error;
			}

			public String getMessage() {
				return message;
			}
		};
	}
}
