package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CartDTO {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CartRequest {
		private Long userId;
		private Long productId;
		private int quantity;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CartItem {
		private Long productId;
		private int quantity;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CartResponse {
		private String message;
		private CartItem cart;
	}
}
