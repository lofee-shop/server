package com.example.server.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.BookmarkRequest;
import com.example.server.dto.response.BookmarkResponse;
import com.example.server.service.BookmarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	public BookmarkController(BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}

	@Operation(summary = "상품 북마크")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "사용자가 상품을 처음 북마크"),
		@ApiResponse(responseCode = "400", description = "productId가 숫자가 아니거나, 잘못된 요청"),
		@ApiResponse(responseCode = "403", description = "로그인되지 않은 사용자 (userId=null 등)"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 상품"),
		@ApiResponse(responseCode = "409", description = "이미 북마크된 상품에 또다시 북마크 요청")
	})
	@PostMapping("/bookmarks")
	public ResponseEntity<?> addBookmark(@RequestBody BookmarkRequest request) {
		// 1) userId null → 403
		if (request.getUserId() == null) {
			return ResponseEntity.status(403).body(
				createErrorResponse("Forbidden", "You must be logged in to bookmark items.")
			);
		}
		// 2) productId null → 400
		if (request.getProductId() == null) {
			return ResponseEntity.badRequest().body(
				createErrorResponse("Bad Request", "Product ID must be a number.")
			);
		}

		// 3) Service 로직
		try {
			BookmarkResponse response = bookmarkService.addBookmark(request);
			// 북마크가 새로 생성 → 201 Created
			return ResponseEntity.status(201).body(response);

		} catch (NoSuchElementException e) {
			// User or Product not found → 404
			return ResponseEntity.status(404).body(createErrorResponse("Not Found", e.getMessage()));

		} catch (IllegalStateException e) {
			// Already bookmarked → 409 Conflict
			return ResponseEntity.status(409)
				.body(createErrorResponse("Conflict", "This product is already bookmarked."));
		}
	}

	private Object createErrorResponse(String error, String message) {
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
