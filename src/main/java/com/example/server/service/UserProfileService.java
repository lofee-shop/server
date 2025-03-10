package com.example.server.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.common.exception.CustomException;
import com.example.server.common.exception.ResponseCode;
import com.example.server.dto.request.UserProfileRequestDto;
import com.example.server.dto.response.UserProfileResponseDto;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

	private final UserRepository userRepository;
	public static final String PROFILE_UPLOAD_DIR = "/uploads/profile/";
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/profile/";

	@Transactional(readOnly = true)
	public UserProfileResponseDto getUserProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
		return new UserProfileResponseDto(user);
	}

	@Transactional
	public UserProfileResponseDto updateProfile(UserProfileRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		user.setNickname(requestDto.getNickname());
		user.setIntroduction(requestDto.getIntroduction());
		user.setProfileImg(requestDto.getProfileImg());

		userRepository.save(user);
		return new UserProfileResponseDto(user);
	}

	@Transactional
	public String uploadProfileImage(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new CustomException(ResponseCode.FILE_UPLOAD_FAILED);
		}

		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path path = Paths.get(UPLOAD_DIR + fileName);

		File directory = new File(UPLOAD_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		file.transferTo(path.toFile());
		return PROFILE_UPLOAD_DIR + fileName;
	}
}
