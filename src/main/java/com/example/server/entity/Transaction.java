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
@Table(name = "transaction", schema = "test1")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private com.example.server.entity.User user;

    @Size(max = 66)
    @NotNull
    @Column(name = "tx_hash", nullable = false, length = 66)
    private String txHash;

    @Size(max = 50)
    @NotNull
    @Column(name = "network", nullable = false, length = 50)
    private String network;

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}