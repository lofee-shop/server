package com.example.server.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.server.entity.enums.VerificationStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seller", schema = "test1")
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(nullable = false)
	private User user;  // FK (Users)

	@Column(nullable = false)
	private String name;  // 실제 판매자 이름

	@Column(nullable = false)
	private String phone;  // 판매자 연락처

	@Column(nullable = false)
	private Boolean isBusiness;  // true: 사업자, false: 개인 판매자

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private VerificationStatus verificationStatus;  // pending, verified, rejected

	@Column(nullable = false)
	private Integer baseShippingFee;  // 기본 배송비

	@Column(nullable = false)
	private Integer freeShippingThreshold;  // 무료배송 기준

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products = new ArrayList<>();
}
