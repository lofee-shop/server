package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.CartItem;
import com.example.server.dto.request.CartRequest;
import com.example.server.dto.response.CartResponse;
import com.example.server.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CartController {

	private final CartService cartService;

	/**
	 * 장바구니에 상품 추가하기
	 */
	@Operation(
		summary = "장바구니에 상품 추가",
		description = "로그인한 사용자가 특정 상품을 장바구니에 추가합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "이미 장바구니에 있는 상품의 수량을 증가한 경우."),
		@ApiResponse(responseCode = "201", description = "장바구니에 새로운 상품을 처음 추가한 경우."),
		@ApiResponse(responseCode = "400", description = "productId가 숫자가 아님, 수량(quantity)이 0 이하."),
		@ApiResponse(responseCode = "403", description = "로그인하지 않고 장바구니 시도 (userId가 null 등)."),
		@ApiResponse(responseCode = "409", description = "재고 부족 등으로 요청 수량을 담을 수 없는 경우.")
	})
	@PostMapping("/cart")
	public ResponseEntity<?> addToCart(@RequestBody CartRequest request) {

		// (1) userId 확인 → 비로그인 사용자
		if (request.getUserId() == null) {
			return ResponseEntity.status(403)
				.body(createErrorResponse("Forbidden", "You must be logged in to add items to the cart."));
		}

		// (2) productId, quantity 검증
		if (request.getProductId() == null || request.getQuantity() <= 0) {
			return ResponseEntity.status(400)
				.body(createErrorResponse("Bad Request", "productId must be a number, quantity must be > 0"));
		}

		// (3) 재고 부족 예시(가정)
		if (request.getQuantity() > 10) {
			return ResponseEntity.status(409)
				.body(createErrorResponse("Conflict", "Requested quantity exceeds available stock."));
		}

		// (4) 실제 DB 로직
		boolean alreadyInCart = cartService.hasCartItem(request.getUserId(), request.getProductId());

		if (alreadyInCart) {
			// 이미 장바구니에 있음 → 수량 업데이트 → 200 OK
			cartService.updateCartItemQuantity(request.getUserId(), request.getProductId(), request.getQuantity());
			CartResponse response = new CartResponse(
				"Cart updated successfully",
				new CartItem(request.getProductId(), request.getQuantity())
			);
			return ResponseEntity.status(200).body(response);
		} else {
			// 새로 추가 → 201 Created
			cartService.createCartItem(request.getUserId(), request.getProductId(), request.getQuantity());
			CartResponse response = new CartResponse(
				"Product added to cart",
				new CartItem(request.getProductId(), request.getQuantity())
			);
			return ResponseEntity.status(201).body(response);
		}
	}

	/**
	 * 에러 응답 JSON 생성 편의 메서드
	 */
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
