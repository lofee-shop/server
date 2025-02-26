package com.example.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class NonceService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public String generateNonce(String walletAddress) {
		String nonce = String.valueOf(new Random().nextInt(900000) + 100000); // 6자리 랜덤 숫자
		redisTemplate.opsForValue().set("nonce:" + walletAddress, nonce, Duration.ofMinutes(10)); // 10분 후 자동 삭제
		return nonce;
	}

	public String getNonce(String walletAddress) {
		return redisTemplate.opsForValue().get("nonce:" + walletAddress);
	}

	public void deleteNonce(String walletAddress) {
		redisTemplate.delete("nonce:" + walletAddress);
	}
}

