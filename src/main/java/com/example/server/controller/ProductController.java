package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.response.ProductDetailResponse;
import com.example.server.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.getProductDetail(productId));
	}
}
