package com.example.server.entity;

import java.math.BigDecimal;
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
import jakarta.persistence.PrePersist;
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
	@Column(name = "product_name", nullable = false)
	private String productName;

	@NotNull
	@Column(name = "price", precision = 10, scale = 2, nullable = false) //(10자리: 8자리 정수 + 2자리 소수점 이하)
	private BigDecimal price;

	@NotNull
	@Column(name = "stock", nullable = false)
	private Integer stock;

	@NotNull
	@Column(name = "status", nullable = false, length = 255) // length 지정(짧은 상태 값을 저장 하기 적합.)
	private String status;

	@Lob // 장문 설명 대비
	@Column(name = "info")
	private String info;

	@ColumnDefault("0")
	@Column(name = "view")
	private Integer view;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = Instant.now();
	}
	// @PrePersist → 엔티티가 처음 영속화(persist)되기 전에 자동으로 실행.
	// Instant.now()로 서버 시간(UTC) 기준을 설정.
	// DB에 의존하지 않고 애플리케이션 레벨에서 일관된 시간 관리.

	@OneToMany(mappedBy = "product")
	private List<Bookmark> bookmarks = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<ProductThumbnail> productThumbnails = new ArrayList<>();

}
