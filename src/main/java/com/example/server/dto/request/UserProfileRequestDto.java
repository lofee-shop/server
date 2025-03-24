package com.example.server.dto.request;

public record UserProfileRequestDto(
	Long userId,
	String nickname,
	String introduction,
	String profileImg,
	String bannerImg
) {
}
