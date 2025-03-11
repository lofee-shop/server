package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
