package com.example.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.common.exception.CustomException;
import com.example.server.common.exception.ResponseCode;
import com.example.server.dto.request.AddressRequestDto;
import com.example.server.dto.response.AddressResponseDto;
import com.example.server.dto.response.AddressesResponseDto;
import com.example.server.entity.Address;
import com.example.server.entity.User;
import com.example.server.repository.AddressRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	@Transactional
	public void addAddress(Long userId, AddressRequestDto requestDto) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		Address address = new Address(
			user,
			requestDto.getRealName(),
			requestDto.getPhoneNumber(),
			requestDto.getAddress(),
			requestDto.getPostalCode()
		);

		addressRepository.save(address);
	}

	@Transactional(readOnly = true)
	public AddressesResponseDto getAddresses(Long userId) {
		List<AddressResponseDto> addresses = addressRepository.findByUserId(userId)
			.stream()
			.map(AddressResponseDto::new)
			.toList();

		return new AddressesResponseDto(addresses);
	}

	@Transactional
	public void deleteAddress(Long addressId) {
		Address address = addressRepository.findById(addressId)
			.orElseThrow(() -> new CustomException(ResponseCode.ADDRESS_NOT_FOUND));

		addressRepository.delete(address);
	}
}
