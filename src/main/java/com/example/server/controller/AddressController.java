package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.AddressRequestDto;
import com.example.server.dto.response.AddressResponseDto;
import com.example.server.dto.response.AddressesResponseDto;
import com.example.server.service.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/addresses")
public class AddressController {

	private final AddressService addressService;

	@PostMapping("/{userId}")
	public ResponseEntity<AddressResponseDto> addAddress(
		@PathVariable Long userId,
		@Valid @RequestBody AddressRequestDto requestDto) {
		AddressResponseDto responseDto = addressService.addAddress(userId, requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<AddressesResponseDto> getAddresses(@PathVariable Long userId) {
		AddressesResponseDto responseDto = addressService.getAddresses(userId);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{addressId}")
	public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
		addressService.deleteAddress(addressId);
		return ResponseEntity.ok("삭제 완료");
	}
}
