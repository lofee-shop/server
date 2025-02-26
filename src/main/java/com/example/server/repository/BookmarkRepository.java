package com.example.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Bookmark;
import com.example.server.entity.Product;
import com.example.server.entity.User;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	// 이미 북마크된 상품인지 체크
	Optional<Bookmark> findByUserAndProduct(User user, Product product);
}
