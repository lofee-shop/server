package com.example.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class NonceService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void saveNonce(String walletAddress, String nonce) {
        String key = "nonce:" + walletAddress;
        redisTemplate.opsForValue().set(key, nonce, Duration.ofMinutes(1));
    }

    public String getNonce(String walletAddress) {
        String key = "nonce:" + walletAddress;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteNonce(String walletAddress) {
        String key = "nonce:" + walletAddress;
        redisTemplate.delete(key);
    }
}

