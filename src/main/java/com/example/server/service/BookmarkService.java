package com.example.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.dto.request.BookmarkRequest;
import com.example.server.dto.response.BookmarkResponse;
import com.example.server.entity.Bookmark;
import com.example.server.entity.Product;
import com.example.server.entity.User;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.repository.BookmarkRepository;
import com.example.server.repository.ProductRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Transactional
	public BookmarkResponse addBookmark(BookmarkRequest request) {
		User user = userRepository.findById(request.userId())
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		Product product = productRepository.findById(request.productId())
			.orElseThrow(() -> new CustomException(ResponseCode.PRODUCT_NOT_FOUND));

		bookmarkRepository.findByUserAndProduct(user, product)
			.ifPresent(b -> {
				throw new CustomException(ResponseCode.ALREADY_BOOKMARKED);
			});

		Bookmark bookmark = new Bookmark();
		bookmark.setUser(user);
		bookmark.setProduct(product);
		bookmarkRepository.save(bookmark);

		return new BookmarkResponse(true);
	}
}
