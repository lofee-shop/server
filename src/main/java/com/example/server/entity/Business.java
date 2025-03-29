package com.example.server.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.server.entity.enums.VerificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "business", schema = "test1")
public class Business {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_id", nullable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String businessNumber;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String businessName;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String businessAddress;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private VerificationStatus verificationStatus;

	@NotNull
	@Column(nullable = false)
	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant verifiedAt;

}