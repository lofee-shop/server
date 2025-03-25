package com.example.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.CartItem;
import com.example.server.dto.request.CartRequest;
import com.example.server.dto.response.CartResponse;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
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

	@Operation(
		summary = "장바구니에 상품 추가",
		description = "로그인한 사용자가 특정 상품을 장바구니에 추가합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "장바구니에 있는 상품의 수량을 증가한 경우."),
		@ApiResponse(responseCode = "201", description = "새로운 상품을 처음 추가한 경우."),
		@ApiResponse(responseCode = "400", description = "productId가 없거나 수량이 0 이하."),
		@ApiResponse(responseCode = "403", description = "로그인하지 않은 사용자."),
		@ApiResponse(responseCode = "409", description = "재고 부족.")
	})
	@PostMapping("/cart")
	public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest request) {

		validateRequest(request);

		boolean alreadyInCart = cartService.hasCartItem(request.userId(), request.productId());

		if (alreadyInCart) {
			cartService.updateCartItemQuantity(request.userId(), request.productId(), request.quantity());

			CartResponse response = new CartResponse(
				"Cart updated successfully",
				new CartItem(request.productId(), request.quantity())
			);
			return ResponseEntity.ok(response);
		} else {
			cartService.createCartItem(request.userId(), request.productId(), request.quantity());

			CartResponse response = new CartResponse(
				"Product added to cart",
				new CartItem(request.productId(), request.quantity())
			);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
	}

	private void validateRequest(CartRequest request) {
		if (request.userId() == null) {
			throw new CustomException(ResponseCode.USER_NOT_FOUND);
		}

		if (request.productId() == null || request.quantity() <= 0) {
			throw new CustomException(ResponseCode.PRODUCT_NOT_FOUND);
		}

		if (request.quantity() > 10) { // 실제 서비스에선 실제 재고량 확인이 필요
			throw new CustomException(ResponseCode.STOCK_NOT_ENOUGH);
		}
	}
}
