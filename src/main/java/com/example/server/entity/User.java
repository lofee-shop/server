package com.example.server.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.example.server.entity.enums.Role;

import jakarta.persistence.CascadeType;
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
	@Column(nullable = false, length = 42)
	private String walletAddress;

	@Size(max = 50)
	@Column(length = 50)
	private String nickname;

	private String profileImg;

	@Size(max = 100)
	@Column(length = 100)
	private String introduction;

	@NotNull
	@Lob
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Boolean isActive = false;

	@ColumnDefault("CURRENT_TIMESTAMP")
	private Instant createdAt;

	@OneToMany(mappedBy = "user")
	private List<Product> products = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Transaction> transactions = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	public void updateProfile(String nickname, String introduction, String profileImg) {
		this.nickname = nickname;
		this.introduction = introduction;
		this.profileImg = profileImg;
	}

}
