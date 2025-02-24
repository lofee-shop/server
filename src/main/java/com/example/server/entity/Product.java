package com.example.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private com.example.server.entity.User user;

    @Size(max = 255)
    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @Size(max = 255)
    @Column(name = "info")
    private String info;

    @ColumnDefault("0")
    @Column(name = "view")
    private Integer view;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "product")
    private Set<Bookmark> bookmarks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<com.example.server.entity.ProductThumbnail> productThumbnails = new LinkedHashSet<>();

}