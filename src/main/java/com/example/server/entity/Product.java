package com.example.server.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product", schema = "test1")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String productName;

	@NotNull
	@Column(nullable = false)
	private Float price;

	@NotNull
	@Column(nullable = false)
	private Integer stock;

	@NotNull
	@Lob
	@Column(nullable = false)
	private String status;

	@Size(max = 255)
	private String info;

	@ColumnDefault("0")
	private Integer view;

	@NotNull
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(nullable = false)
	private Instant createdAt;

	@OneToMany(mappedBy = "product")
	private List<Bookmark> bookmarks = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<Cart> carts = new ArrayList<>();
}
