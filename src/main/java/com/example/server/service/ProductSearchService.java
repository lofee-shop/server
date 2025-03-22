package com.example.server.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.dto.response.ProductSearchResponse;
import com.example.server.repository.ProductSearchRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

	private final ProductSearchRepository productSearchRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public ProductSearchResponse getSuggestions(String query) {

		List<ProductSearchResponse.Suggestion> productSuggestions =
			productSearchRepository.findTop5ByNameContainingIgnoreCase(query)
				.stream()
				.map(product -> new ProductSearchResponse.Suggestion(
					"product", product.getId(), product.getName()))
				.toList();

		List<ProductSearchResponse.Suggestion> userSuggestions =
			userRepository.findTop5ByNicknameContainingIgnoreCase(query)
				.stream()
				.map(user -> new ProductSearchResponse.Suggestion(
					"user", user.getId(), user.getNickname()))
				.toList();

		// 상품과 사용자 검색결과를 합쳐서 최대 5개만 추출
		List<ProductSearchResponse.Suggestion> suggestions = Stream
			.concat(productSuggestions.stream(), userSuggestions.stream())
			.limit(5)
			.collect(Collectors.toList());

		return new ProductSearchResponse(suggestions); // 찾은 결과가 없으면 빈 리스트가 반환됨
	}
}
