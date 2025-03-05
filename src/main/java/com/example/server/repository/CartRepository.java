package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
