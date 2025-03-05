package com.example.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.server.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
}
