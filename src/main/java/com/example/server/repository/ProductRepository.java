package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
