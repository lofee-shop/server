package com.example.server.dto.request;

public record CartRequest(Long userId, Long productId, int quantity) {
}
