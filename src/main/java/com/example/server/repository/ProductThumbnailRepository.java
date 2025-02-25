package com.example.server.repository;

import com.example.server.entity.ProductThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductThumbnailRepository extends JpaRepository<ProductThumbnail, Long> {
}
