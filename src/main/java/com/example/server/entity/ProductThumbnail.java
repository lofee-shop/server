package com.example.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "product_thumbnails", schema = "test1")
public class ProductThumbnail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thumbnail_id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Size(max = 255)
	@NotNull
	@Column(name = "img_path", nullable = false)
	private String imgPath;

	@NotNull
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

}