package com.example.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.BookmarkRequest;
import com.example.server.dto.response.BookmarkResponse;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.service.BookmarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@Operation(summary = "상품 북마크")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "상품을 처음 북마크함"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 (productId가 없거나 숫자가 아님)"),
		@ApiResponse(responseCode = "403", description = "로그인되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 상품 또는 사용자"),
		@ApiResponse(responseCode = "409", description = "이미 북마크한 상품")
	})
	@PostMapping("/")
	public ResponseEntity<BookmarkResponse> addBookmark(@RequestBody BookmarkRequest request) {
		validateRequest(request);

		BookmarkResponse response = bookmarkService.addBookmark(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	private void validateRequest(BookmarkRequest request) {
		if (request.userId() == null) {
			throw new CustomException(ResponseCode.USER_NOT_FOUND);
		}

		if (request.productId() == null) {
			throw new CustomException(ResponseCode.PRODUCT_NOT_FOUND);
		}
	}
}
