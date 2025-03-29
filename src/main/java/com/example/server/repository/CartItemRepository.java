package com.example.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.CartItem;
import com.example.server.entity.Product;
import com.example.server.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	Optional<CartItem> findByUserAndProduct(User user, Product product);
}
