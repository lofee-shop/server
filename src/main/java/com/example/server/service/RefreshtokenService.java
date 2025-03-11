package com.example.server.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RefreshtokenService {

	@Autowired
	@Qualifier("refreshTokenRedisTemplate")
	private StringRedisTemplate redisTemplate;

	public void saveRefreshToken(Long userId, String refreshToken) {
		String key = "refresh:" + userId;
		redisTemplate.opsForValue().set(key, refreshToken, Duration.ofDays(14)); // 14일 저장
	}

	public String getRefreshToken(Long userId) {
		String key = "refresh:" + userId;
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteRefreshToken(Long userId) {
		String key = "refresh:" + userId;
		redisTemplate.delete(key);
	}
}
