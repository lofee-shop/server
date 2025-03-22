package com.example.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Product;

public interface ProductSearchRepository extends JpaRepository<Product, Long> {

	List<Product> findTop5ByNameContainingIgnoreCase(String name);
}
