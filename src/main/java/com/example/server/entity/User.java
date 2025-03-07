package com.example.server.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "test1")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long id;

	@Size(max = 42)
	@NotNull
	@Column(name = "wallet_address", nullable = false, length = 42)
	private String walletAddress;

	@Size(max = 50)
	@Column(name = "nickname", nullable = true, length = 50)
	private String nickname;

	@Column(name = "profile_img", nullable = true)
	private String profileImg;

	@Size(max = 100)
	@Column(name = "introduction", nullable = true, length = 100)
	private String introduction;

	@NotNull
	@Lob
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@NotNull
	@Column(name = "is_active", nullable = false)
	private Boolean isActive = false;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "created_at")
	private Instant createdAt;

	@OneToMany(mappedBy = "user")
	private List<Product> products = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Transaction> transactions = new ArrayList<>();

}
