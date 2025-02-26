package com.example.server.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.dto.BookmarkDTO.BookmarkRequest;
import com.example.server.dto.BookmarkDTO.BookmarkResponse;
import com.example.server.entity.Bookmark;
import com.example.server.entity.Product;
import com.example.server.entity.User;
import com.example.server.repository.BookmarkRepository;
import com.example.server.repository.ProductRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	/**
	 * 상품 북마크(추가)
	 */
	@Transactional
	public BookmarkResponse addBookmark(BookmarkRequest request) {
		// 1) 유저 & 상품 조회
		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new NoSuchElementException("User not found"));

		Product product = productRepository.findById(request.getProductId())
			.orElseThrow(() -> new NoSuchElementException("Product not found"));

		// 2) 이미 북마크되었는지 확인
		boolean alreadyBookmarked = bookmarkRepository.findByUserAndProduct(user, product).isPresent();
		if (alreadyBookmarked) {
			// 이미 존재 → Conflict 상황
			throw new IllegalStateException("Already bookmarked");
		}

		// 3) 새로 북마크
		Bookmark bookmark = new Bookmark();
		bookmark.setUser(user);
		bookmark.setProduct(product);
		// createdAt → @PrePersist
		bookmarkRepository.save(bookmark);

		// 4) 응답
		return new BookmarkResponse("Bookmark added", true);
	}
}
