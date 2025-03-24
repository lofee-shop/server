package com.example.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRequestDto(
	Long addressId,

	@NotBlank(message = "이름을 입력하지 않았습니다.") String realName,

	@Pattern(regexp = "^010-?\\d{4}-?\\d{4}$",
		message = "올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678 또는 01012345678)") String phoneNumber,

	@NotBlank(message = "주소를 입력하지 않았습니다.") String address,

	@NotBlank(message = "우편번호를 입력하지 않았습니다.") String postalCode,

	String addressDetail,

	String deliveryRequest,

	boolean isDefault
) {
}
