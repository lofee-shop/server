package com.example.server.repository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByWalletAddress(String walletAddress);

	Optional<User> findById(Long userid);

	List<User> findTop5ByNicknameContainingIgnoreCase(String query);
}
