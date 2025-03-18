package com.example.server.dto.response;

import com.example.server.entity.enums.Role;

public record UserProfileResponseDto(
	Long userId,
	String nickname,
	String introduction,
	String profileImg,
	Role role
) {
}
