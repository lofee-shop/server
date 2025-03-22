package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.response.ProductSearchResponse;
import com.example.server.service.ProductSearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class ProductSearchController {

	private final ProductSearchService productSearchService;

	@Operation(summary = "키워드 검색어 조회")
	@ApiResponse(responseCode = "200", description = "성공적으로 키워드 검색어 목록 조회")
	@GetMapping("/suggestions")
	public ResponseEntity<ProductSearchResponse> getSuggestions(@RequestParam("q") String query) {
		ProductSearchResponse response = productSearchService.getSuggestions(query);
		return ResponseEntity.ok(response);
	}
}
