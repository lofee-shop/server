package com.example.server.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshtokenService {

	@Qualifier("refreshTokenRedisTemplate")
	private final StringRedisTemplate redisTemplate;

	private static final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(14);

	@Transactional
	public void saveRefreshToken(Long userId, String refreshToken) {
		String key = "refresh:" + userId;
		redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRATION); // 14일 저장
	}

	public String getRefreshToken(Long userId) {
		String key = "refresh:" + userId;
		return redisTemplate.opsForValue().get(key);
	}

	@Transactional
	public void deleteRefreshToken(Long userId) {
		String key = "refresh:" + userId;
		redisTemplate.delete(key);
	}
}
