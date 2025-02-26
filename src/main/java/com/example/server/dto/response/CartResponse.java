package com.example.server.dto.response;

import com.example.server.dto.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
	private String message;
	private CartItem cart;
}
