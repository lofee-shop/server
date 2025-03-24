package com.example.server.entity.enums;

import java.util.Arrays;

import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;

import lombok.Getter;

@Getter
public enum UploadType {
	PROFILE("profile", "/uploads/profile/", "profile/"),
	BANNER("banner", "/uploads/banner/", "banner/");

	private final String type;
	private final String returnPath;
	private final String subDirectory;

	UploadType(String type, String returnPath, String subDirectory) {
		this.type = type;
		this.returnPath = returnPath;
		this.subDirectory = subDirectory;
	}

	public static UploadType getType(String type) {
		return Arrays.stream(values())
			.filter(t -> t.getType().equals(type))
			.findFirst()
			.orElseThrow(() -> new CustomException(ResponseCode.INVALID_IMAGE_TYPE));
	}
}
