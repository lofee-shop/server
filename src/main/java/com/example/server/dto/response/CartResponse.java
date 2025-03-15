package com.example.server.dto.response;

import com.example.server.dto.CartItem;

public record CartResponse(String message, CartItem cart) {
}
