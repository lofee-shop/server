package com.example.server.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NonceService {

	@Qualifier("nonceRedisTemplate")
	private final StringRedisTemplate redisTemplate;

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int NONCE_LENGTH = 8;
	private static final SecureRandom RANDOM = new SecureRandom();

	@Transactional
	public String generateNonce(String walletAddress) {
		walletAddress = extractWalletAddress(walletAddress);
		StringBuilder nonce = new StringBuilder(NONCE_LENGTH);
		for (int i = 0; i < NONCE_LENGTH; i++) {
			nonce.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		redisTemplate.opsForValue()
			.set("nonce:" + walletAddress, String.valueOf(nonce), Duration.ofMinutes(10)); // 10분 후 자동 삭제
		return nonce.toString();
	}

	public String getNonce(String walletAddress) {
		return redisTemplate.opsForValue().get("nonce:" + walletAddress);
	}

	@Transactional
	public void deleteNonce(String walletAddress) {
		redisTemplate.delete("nonce:" + walletAddress);
	}

	private String extractWalletAddress(String walletAddress) {
		try {
			if (walletAddress.startsWith("{")) {
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, String> map = objectMapper.readValue(walletAddress, Map.class);
				return map.get("walletAddress");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return walletAddress;
	}
}

