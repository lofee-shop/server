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
import com.example.server.dto.response.AddressesResponseDto;
import com.example.server.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/addresses")
public class AddressController {

	private final AddressService addressService;

	@Operation(summary = "주소 추가", description = "사용자가 새로운 주소를 추가합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "주소 추가 성공"),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
	})
	@PostMapping("/{userId}")
	public ResponseEntity<Void> addAddress(
		@PathVariable Long userId,
		@Valid @RequestBody AddressRequestDto requestDto) {
		addressService.addAddress(userId, requestDto);
		return ResponseEntity.status(201).build();
	}

	@Operation(summary = "주소 목록 조회", description = "사용자의 주소 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "주소 목록 조회 성공",
			content = @Content(schema = @Schema(implementation = AddressesResponseDto.class)))
	})
	@GetMapping("/{userId}")
	public ResponseEntity<AddressesResponseDto> getAddresses(@PathVariable Long userId) {
		AddressesResponseDto responseDto = addressService.getAddresses(userId);
		return ResponseEntity.ok(responseDto);
	}

	@Operation(summary = "주소 삭제", description = "사용자의 특정 주소를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "삭제 완료"),
		@ApiResponse(responseCode = "404", description = "배송지를 찾을 수 없습니다.")
	})
	@DeleteMapping("/{addressId}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
		addressService.deleteAddress(addressId);
		return ResponseEntity.status(201).build();
	}
}
