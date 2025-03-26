package com.example.server.dto.response;

public record AddressResponseDto(
	Long id,
	String realName,
	String phoneNumber,
	String address,
	String postalCode,
	String addressDetail,
	String deliveryRequest,
	boolean isDefault,
	Long userId
) {
}
