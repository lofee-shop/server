package com.example.server.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.example.server.entity.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
	@NotNull
	@Column(name = "nickname", nullable = false, length = 50)
	private String nickname;

	@NotNull
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

	public User(String walletAddress, Role role, Boolean isActive) {
		this.walletAddress = walletAddress;
		this.role = role;
		this.isActive = isActive;
	}
}