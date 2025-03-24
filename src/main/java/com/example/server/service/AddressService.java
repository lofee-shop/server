package com.example.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.dto.request.AddressRequestDto;
import com.example.server.dto.response.AddressResponseDto;
import com.example.server.dto.response.AddressesResponseDto;
import com.example.server.entity.Address;
import com.example.server.entity.User;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.repository.AddressRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	@Transactional
	public void updateAddress(Long userId, AddressRequestDto requestDto) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		List<Address> existingAddresses = addressRepository.findByUserId(user.getId());
		if (requestDto.addressId() == null && existingAddresses.size() >= 5) {
			throw new CustomException(ResponseCode.ADDRESS_LIMIT_EXCEEDED);
		}

		if (requestDto.isDefault()) {
			existingAddresses.forEach(address -> address.setDefault(false));
		}

		Address address;

		if (requestDto.addressId() != null) {
			address = addressRepository.findById(requestDto.addressId())
				.orElseThrow(() -> new CustomException(ResponseCode.ADDRESS_NOT_FOUND));

			address.update(requestDto);
		} else {
			address = new Address(user, requestDto);
		}

		addressRepository.save(address);

		boolean hasDefault = addressRepository.existsByUserIdAndIsDefaultTrue(userId);
		if (!hasDefault) {
			address.setDefault(true);
		}
	}

	public AddressesResponseDto getAddresses(Long userId) {
		List<AddressResponseDto> addresses = addressRepository.findByUserIdOrderByIsDefaultDescUpdatedAtDesc(userId)
			.stream()
			.map(address -> new AddressResponseDto(
				address.getId(),
				address.getRealName(),
				address.getPhoneNumber(),
				address.getAddress(),
				address.getPostalCode(),
				address.getAddressDetail(),
				address.getDeliveryRequest(),
				address.isDefault(),
				address.getUser().getId()
			))
			.toList();

		return new AddressesResponseDto(addresses);
	}

	@Transactional
	public void deleteAddress(Long userId, Long addressId) {
		Address address = addressRepository.findById(addressId)
			.orElseThrow(() -> new CustomException(ResponseCode.ADDRESS_NOT_FOUND));

		boolean wasDefault = address.isDefault();
		addressRepository.delete(address);

		if (wasDefault) {
			List<Address> addresses = addressRepository.findByUserIdOrderByUpdatedAtDesc(userId);
			if (!addresses.isEmpty()) {
				addresses.get(0).setDefault(true);
			}
		}
	}
}
