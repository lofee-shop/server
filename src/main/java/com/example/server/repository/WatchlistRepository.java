package com.example.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Product;
import com.example.server.entity.User;
import com.example.server.entity.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

	// 이미 북마크된 상품인지 체크
	Optional<Watchlist> findByUserAndProduct(User user, Product product);

	boolean existsByProductId(Long productId);
}
