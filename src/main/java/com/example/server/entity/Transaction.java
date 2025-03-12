package com.example.server.entity;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transaction", schema = "test1")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Size(max = 66)
	@NotNull
	@Column(nullable = false, length = 66)
	private String txHash;

	@Size(max = 50)
	@NotNull
	@Column(nullable = false, length = 50)
	private String network;

	@NotNull
	@Lob
	@Column(nullable = false)
	private String status;

	@NotNull
	@Column(nullable = false)
	private Float totalAmount;

	@NotNull
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(nullable = false)
	private Instant createdAt;

}
