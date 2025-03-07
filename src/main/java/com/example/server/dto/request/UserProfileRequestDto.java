package com.example.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequestDto {
	private Long userId;
	private String nickname;
	private String introduction;
	private String profileImg;
}
