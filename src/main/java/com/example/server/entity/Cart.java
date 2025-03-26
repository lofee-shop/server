package com.example.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "cart", schema = "test1")
public class Cart {
	@Id
	@Column(name = "cart_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Product product;

	@NotNull
	@ColumnDefault("1")
	@Column(nullable = false)
	private Integer quantity;

	@NotNull
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(nullable = false)
	private Instant createdAt;

}