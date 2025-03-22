package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	// 필요 시 기본적인 CRUD 외에도 추가 메서드 정의 가능
	// 예) Optional<Product> findByName(String name);
}
