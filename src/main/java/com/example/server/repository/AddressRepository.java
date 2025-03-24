package com.example.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findByUserId(Long userId);

	List<Address> findByUserIdOrderByIsDefaultDescUpdatedAtDesc(Long userId);

	boolean existsByUserIdAndIsDefaultTrue(Long userId);

	List<Address> findByUserIdOrderByUpdatedAtDesc(Long userId);
}
