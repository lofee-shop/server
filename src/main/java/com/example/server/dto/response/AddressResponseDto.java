package com.example.server.dto.response;

public record AddressResponseDto(
	Long id,
	String realName,
	String phoneNumber,
	String address,
	String postalCode,
	Long userId
) {
}
