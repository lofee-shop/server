package com.example.server.dto.response;

import com.example.server.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressResponseDto {
	private Long id;
	private String realName;
	private String phoneNumber;
	private String address;
	private String postalCode;
	private Long userId;

	public AddressResponseDto(Address address) {
		this.id = address.getId();
		this.realName = address.getRealName();
		this.phoneNumber = address.getPhoneNumber();
		this.address = address.getAddress();
		this.postalCode = address.getPostalCode();
		this.userId = address.getUser().getId();
	}
}
