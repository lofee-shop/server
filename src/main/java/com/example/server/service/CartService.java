package com.example.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.entity.CartItem;
import com.example.server.entity.Product;
import com.example.server.entity.User;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.repository.CartItemRepository;
import com.example.server.repository.ProductSearchRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

	private final CartItemRepository cartItemRepository;
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
		return cartItemRepository.findByUserAndProduct(user, product).isPresent();
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
		CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
			.orElseThrow(() -> new CustomException(ResponseCode.CART_ITEM_NOT_FOUND));

		// 기존 수량 + 추가 수량
		cartItem.setQuantity(cartItem.getQuantity() + quantityToAdd);
		cartItemRepository.save(cartItem);
	}

	/**
	 * 3) 장바구니 새 항목 생성

	 @Transactional public void createCartItem(Long userId, Long productId, int quantity) {
	 User user = userRepository.findById(userId)
	 .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
	 Product product = productRepository.findById(productId)
	 .orElseThrow(() -> new CustomException(ResponseCode.PRODUCT_NOT_FOUND));

	 Cart cart = user.getCart();
	 if (cart == null) {
	 // 만약 Cart가 없으면 새로 생성 (한 유저=한 카트라는 전제)
	 cart = new Cart();
	 cart.setUser(user);

	 CartItem newCartItem = new CartItem();
	 newCartItem.setCart(cart);        // 이 cart는 이미 user와 1:1인 장바구니 컨테이너
	 newCartItem.setProduct(product);
	 newCartItem.setQuantity(quantity);
	 cartItemRepository.save(newCartItem);
	 }
	 }*/
}
