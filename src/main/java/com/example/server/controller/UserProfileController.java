package com.example.server.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.dto.request.UserProfileRequestDto;
import com.example.server.dto.response.UserProfileResponseDto;
import com.example.server.service.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

	private final UserProfileService userProfileService;

	@Operation(summary = "사용자 프로필 조회", description = "사용자의 프로필 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로필 조회 성공",
			content = @Content(schema = @Schema(implementation = UserProfileResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
	})
	@GetMapping("/{userId}")
	public ResponseEntity<UserProfileResponseDto> getUserProfile(@PathVariable Long userId) {
		UserProfileResponseDto responseDto = userProfileService.getUserProfile(userId);
		return ResponseEntity.ok(responseDto);
	}

	@Operation(summary = "프로필 정보 수정", description = "사용자의 프로필 정보를 업데이트합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "프로필 업데이트 성공"),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
	})
	@PutMapping("/update")
	public ResponseEntity<UserProfileResponseDto> updateUserProfile(
		@RequestBody UserProfileRequestDto requestDto) {
		UserProfileResponseDto responseDto = userProfileService.updateProfile(requestDto);
		return ResponseEntity.status(201).body(responseDto);
	}

	@Operation(summary = "프로필 이미지 업로드", description = "사용자의 프로필 이미지를 업로드합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "이미지 업로드 성공",
			content = @Content(schema = @Schema(implementation = String.class))),
		@ApiResponse(responseCode = "400", description = "파일 업로드 실패"),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@PostMapping("/upload-image")
	public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file) throws IOException {
		String filePath = userProfileService.uploadProfileImage(file);
		return ResponseEntity.status(201).body(filePath);
	}
}
