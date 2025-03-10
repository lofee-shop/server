package com.example.server.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class AddressesResponseDto {

	private final List<AddressResponseDto> addresses;

	public AddressesResponseDto(List<AddressResponseDto> addresses) {
		this.addresses = addresses;
	}
}
