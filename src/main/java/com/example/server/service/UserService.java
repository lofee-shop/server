package com.example.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.server.entity.User;
import com.example.server.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Long findUserIdbyWalletAddress(String walletAddress) {
		User user = userRepository.findByWalletAddress(walletAddress)
			.orElseThrow();
		return user.getId();
	}

	public String findWalletAddressByUserId(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow();
		return user.getWalletAddress();
	}

}
