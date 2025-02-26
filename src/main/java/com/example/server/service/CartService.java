package com.example.server.service;

import com.example.server.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
}
