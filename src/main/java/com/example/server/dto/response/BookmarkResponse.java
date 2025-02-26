package com.example.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkResponse {
	private String message;   // 결과 메시지
	private boolean bookmarked; // true = 북마크 성공
}
