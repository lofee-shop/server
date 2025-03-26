/*
package com.example.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.server.entity.Cart;
import com.example.server.entity.Product;
import com.example.server.entity.User;
import com.example.server.repository.CartRepository;
import com.example.server.repository.ProductRepository;
import com.example.server.repository.UserRepository;
import com.example.server.service.CartService;

@ExtendWith(SpringExtension.class)
class CartServiceTest {

	@Mock
	private CartRepository cartRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private CartService cartService;

	private User mockUser;
	private Product mockProduct;
	private Cart existingCart;

	@BeforeEach
	void setUp() {
		// 공통 Mock 객체 초기화
		mockUser = new User();
		mockUser.setId(5L);
		mockUser.setNickname("testUser");

		mockProduct = new Product();
		mockProduct.setId(1L);
		mockProduct.setProductName("TestProduct");
		mockProduct.setStock(10);

		existingCart = new Cart();
		existingCart.setId(99L);
		existingCart.setUser(mockUser);
		existingCart.setProduct(mockProduct);
		existingCart.setQuantity(3);
	}

	@Test
	void hasCartItem_ShouldReturnTrue_WhenCartExists() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(1L)).willReturn(Optional.of(mockProduct));
		given(cartRepository.findByUserAndProduct(mockUser, mockProduct))
			.willReturn(Optional.of(existingCart));

		// when
		boolean result = cartService.hasCartItem(5L, 1L);

		// then
		assertTrue(result);
	}

	@Test
	void hasCartItem_ShouldReturnFalse_WhenCartNotExist() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(1L)).willReturn(Optional.of(mockProduct));
		given(cartRepository.findByUserAndProduct(mockUser, mockProduct))
			.willReturn(Optional.empty());

		// when
		boolean result = cartService.hasCartItem(5L, 1L);

		// then
		assertFalse(result);
	}

	@Test
	void hasCartItem_ShouldThrow_WhenUserNotFound() {
		// given
		given(userRepository.findById(999L)).willReturn(Optional.empty());

		// when & then
		assertThrows(NoSuchElementException.class,
			() -> cartService.hasCartItem(999L, 1L),
			"User not found");
	}

	@Test
	void hasCartItem_ShouldThrow_WhenProductNotFound() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(999L)).willReturn(Optional.empty());

		// when & then
		assertThrows(NoSuchElementException.class,
			() -> cartService.hasCartItem(5L, 999L),
			"Product not found");
	}

	@Test
	void updateCartItemQuantity_ShouldUpdateQuantity_WhenCartExists() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(1L)).willReturn(Optional.of(mockProduct));
		given(cartRepository.findByUserAndProduct(mockUser, mockProduct))
			.willReturn(Optional.of(existingCart));

		// when
		cartService.updateCartItemQuantity(5L, 1L, 2);

		// then
		assertEquals(5, existingCart.getQuantity()); // 3 + 2
		verify(cartRepository).save(existingCart);
	}

	@Test
	void updateCartItemQuantity_ShouldThrow_WhenUserNotFound() {
		// given
		given(userRepository.findById(999L)).willReturn(Optional.empty());

		// when & then
		assertThrows(NoSuchElementException.class,
			() -> cartService.updateCartItemQuantity(999L, 1L, 2),
			"User not found");
	}

	@Test
	void updateCartItemQuantity_ShouldThrow_WhenProductNotFound() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(999L)).willReturn(Optional.empty());

		// when & then
		assertThrows(NoSuchElementException.class,
			() -> cartService.updateCartItemQuantity(5L, 999L, 2),
			"Product not found");
	}

	@Test
	void updateCartItemQuantity_ShouldThrow_WhenCartNotFound() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(1L)).willReturn(Optional.of(mockProduct));
		given(cartRepository.findByUserAndProduct(mockUser, mockProduct))
			.willReturn(Optional.empty());

		// when & then
		assertThrows(NoSuchElementException.class,
			() -> cartService.updateCartItemQuantity(5L, 1L, 2),
			"Cart item not found");
	}

	@Test
	void createCartItem_ShouldSaveNewCart() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(1L)).willReturn(Optional.of(mockProduct));

		ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);

		// when
		cartService.createCartItem(5L, 1L, 2);

		// then
		verify(cartRepository).save(cartCaptor.capture());
		Cart savedCart = cartCaptor.getValue();
		assertEquals(2, savedCart.getQuantity());
		assertEquals(mockUser, savedCart.getUser());
		assertEquals(mockProduct, savedCart.getProduct());
	}

	@Test
	void createCartItem_ShouldThrow_WhenUserNotFound() {
		// given
		given(userRepository.findById(999L)).willReturn(Optional.empty());

		// when & then
		assertThrows(NoSuchElementException.class,
			() -> cartService.createCartItem(999L, 1L, 2),
			"User not found");
	}

	@Test
	void createCartItem_ShouldThrow_WhenProductNotFound() {
		// given
		given(userRepository.findById(5L)).willReturn(Optional.of(mockUser));
		given(productRepository.findById(999L)).willReturn(Optional.empty());

		// when & then
		assertThrows(NoSuchElementException.class,
			() -> cartService.createCartItem(5L, 999L, 2),
			"Product not found");
	}
}
*/
