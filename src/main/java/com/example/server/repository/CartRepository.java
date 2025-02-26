package com.example.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Cart;
import com.example.server.entity.Product;
import com.example.server.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

	// 이미 장바구니에 있는지 판단하기 위해
	Optional<Cart> findByUserAndProduct(User user, Product product);
}
