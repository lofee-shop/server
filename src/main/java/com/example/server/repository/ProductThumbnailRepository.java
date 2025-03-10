package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.ProductThumbnail;

public interface ProductThumbnailRepository extends JpaRepository<ProductThumbnail, Long> {
}
