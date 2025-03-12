package com.example.server.dto.response;

import com.example.server.entity.User;
import com.example.server.entity.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponseDto {
	private Long userId;
	private String nickname;
	private String introduction;
	private String profileImg;
	private Role role;

	public UserProfileResponseDto(User user) {
		this.userId = user.getId();
		this.nickname = user.getNickname();
		this.introduction = user.getIntroduction();
		this.profileImg = user.getProfileImg();
		this.role = user.getRole();
	}
}
