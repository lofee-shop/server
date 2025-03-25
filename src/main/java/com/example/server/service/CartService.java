package com.example.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.entity.Cart;
import com.example.server.entity.Product;
import com.example.server.entity.User;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.repository.CartRepository;
import com.example.server.repository.ProductSearchRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

	private final CartRepository cartRepository;
	private final ProductSearchRepository productRepository;
	private final UserRepository userRepository;

	/**
	 * 1) 이미 장바구니에 있는지 체크
	 */
	public boolean hasCartItem(Long userId, Long productId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ResponseCode.PRODUCT_NOT_FOUND));
		return cartRepository.findByUserAndProduct(user, product).isPresent();
	}

	/**
	 * 2) 장바구니 수량 업데이트 (이미 있는 상품)
	 */
	@Transactional
	public void updateCartItemQuantity(Long userId, Long productId, int quantityToAdd) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ResponseCode.PRODUCT_NOT_FOUND));
		Cart cart = cartRepository.findByUserAndProduct(user, product)
			.orElseThrow(() -> new CustomException(ResponseCode.CART_ITEM_NOT_FOUND));

		// 수량 누적
		cart.setQuantity(cart.getQuantity() + quantityToAdd);
		cartRepository.save(cart);
	}

	/**
	 * 3) 장바구니 새 항목 생성
	 */
	@Transactional
	public void createCartItem(Long userId, Long productId, int quantity) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ResponseCode.PRODUCT_NOT_FOUND));

		Cart newCart = new Cart();
		newCart.setUser(user);
		newCart.setProduct(product);
		newCart.setQuantity(quantity);
		// createdAt은 @PrePersist
		cartRepository.save(newCart);
	}
}
