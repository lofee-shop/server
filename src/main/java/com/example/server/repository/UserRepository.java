package com.example.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findTop5ByNicknameContainingIgnoreCase(String nickname);
}
