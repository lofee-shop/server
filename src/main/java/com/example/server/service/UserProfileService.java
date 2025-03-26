package com.example.server.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.dto.request.UserProfileRequestDto;
import com.example.server.dto.response.UserProfileResponseDto;
import com.example.server.entity.User;
import com.example.server.entity.enums.UploadType;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserProfileService {

	private final UserRepository userRepository;
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload/";

	public UserProfileResponseDto getUserProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
		return new UserProfileResponseDto(
			user.getId(),
			user.getNickname(),
			user.getIntroduction(),
			user.getProfileImg(),
			user.getBannerImg(),
			user.getRole()
		);
	}

	@Transactional
	public UserProfileResponseDto updateProfile(UserProfileRequestDto requestDto) {
		User user = userRepository.findById(requestDto.userId())
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		user.updateProfile(
			requestDto.nickname(),
			requestDto.introduction(),
			requestDto.profileImg(),
			requestDto.bannerImg()
		);

		return new UserProfileResponseDto(
			user.getId(),
			user.getNickname(),
			user.getIntroduction(),
			user.getProfileImg(),
			user.getBannerImg(),
			user.getRole()
		);
	}

	@Transactional
	public String uploadProfileImage(MultipartFile file, String type) throws IOException {
		if (file.isEmpty()) {
			throw new CustomException(ResponseCode.FILE_UPLOAD_FAILED);
		}

		UploadType uploadType = UploadType.getType(type);

		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		String fullUploadDir = UPLOAD_DIR + uploadType.getSubDirectory();

		File directory = new File(fullUploadDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		Path filePath = Paths.get(fullUploadDir + fileName);
		file.transferTo(filePath.toFile());

		return uploadType.getReturnPath() + fileName;
	}
}
