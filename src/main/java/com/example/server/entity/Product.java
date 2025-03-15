package com.example.server.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.example.server.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Column(nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(nullable = false)
	private User user;

	@Size(max = 255)
	@NotNull
	@Column(nullable = false)
	private String productName;

	@NotNull
	@Column(precision = 10, scale = 2, nullable = false) //(10자리: 8자리 정수 + 2자리 소수점 이하)
	private BigDecimal price;

	@NotNull
	@Column(nullable = false)
	private Integer stock;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	@Lob // 장문 설명 대비
	private String info;

	@ColumnDefault("0")
	private Integer view;

	/*
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = Instant.now();
	}
	// @PrePersist → 엔티티가 처음 영속화(persist)되기 전에 자동으로 실행.
	// Instant.now()로 서버 시간(UTC) 기준을 설정.
	// DB에 의존하지 않고 애플리케이션 레벨에서 일관된 시간 관리.
	 */

	@ColumnDefault("CURRENT_TIMESTAMP")
	private Instant createdAt;

	@OneToMany(mappedBy = "product")
	private List<Bookmark> bookmarks = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<ProductThumbnail> productThumbnails = new ArrayList<>();

}
