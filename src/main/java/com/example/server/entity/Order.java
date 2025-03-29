package com.example.server.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`order`", schema = "test1")
public class Order {
	@Id
	@Column(name = "order_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String txHash;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String name;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String phone;

	@NotNull
	@Column(nullable = false)
	private Integer zipNo;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String roadAddr;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String addrDetail;

	@NotNull
	@CreationTimestamp
	@Column(nullable = false)
	private Instant createdAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

}