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
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

	private final UserRepository userRepository;
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/profile/";

	@Transactional(readOnly = true)
	public UserProfileResponseDto getUserProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 user_id를 가진 사용자가 없습니다."));
		return new UserProfileResponseDto(user);
	}

	@Transactional
	public UserProfileResponseDto updateProfile(UserProfileRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 user_id를 사진 사용자가 없습니다."));

		user.setNickname(requestDto.getNickname());
		user.setIntroduction(requestDto.getIntroduction());
		user.setProfileImg(requestDto.getProfileImg());

		userRepository.save(user);
		return new UserProfileResponseDto(user);
	}

	@Transactional
	public String uploadProfileImage(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("파일이 비어있습니다.");
		}

		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path path = Paths.get(UPLOAD_DIR + fileName);

		File directory = new File(UPLOAD_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		file.transferTo(path.toFile());
		return "/uploads/profile/" + fileName;
	}
}
