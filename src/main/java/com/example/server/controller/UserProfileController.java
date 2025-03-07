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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

	private final UserProfileService userProfileService;
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/profile/";

	@GetMapping("/{userId}")
	public ResponseEntity<UserProfileResponseDto> getUserProfile(@PathVariable Long userId) {
		UserProfileResponseDto responseDto = userProfileService.getUserProfile(userId);
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/update")
	public ResponseEntity<UserProfileResponseDto> updateUserProfile(@RequestBody UserProfileRequestDto requestDto) {
		UserProfileResponseDto responseDto = userProfileService.updateProfile(requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/upload-image")
	public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file) {
		try {
			String filePath = userProfileService.uploadProfileImage(file);
			return ResponseEntity.ok(filePath);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("파일 업로드 실패 : " + e.getMessage());
		}
	}
}
