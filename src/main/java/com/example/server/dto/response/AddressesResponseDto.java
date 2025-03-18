package com.example.server.dto.response;

import java.util.List;

public record AddressesResponseDto(
	List<AddressResponseDto> addresses
) {
}
