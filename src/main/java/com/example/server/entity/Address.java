package com.example.server.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.server.dto.request.AddressRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "address", schema = "test1")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "name", nullable = false)
	private String realName;

	@Column(name = "phone", nullable = false)
	private String phoneNumber;

	@Column(name = "zip_no", nullable = false)
	private String postalCode;

	@Column(name = "road_addr", nullable = false)
	private String address;

	private String addressDetail;

	private String deliveryRequest;

	@Column(nullable = false)
	private boolean isDefault;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}

	public Address(User user, AddressRequestDto dto) {
		this.user = user;
		this.realName = dto.realName();
		this.phoneNumber = dto.phoneNumber();
		this.address = dto.address();
		this.postalCode = dto.postalCode();
		this.addressDetail = dto.addressDetail();
		this.deliveryRequest = dto.deliveryRequest();
		this.isDefault = dto.isDefault();
	}

	public void update(AddressRequestDto dto) {
		this.realName = dto.realName();
		this.phoneNumber = dto.phoneNumber();
		this.address = dto.address();
		this.postalCode = dto.postalCode();
		this.addressDetail = dto.addressDetail();
		this.deliveryRequest = dto.deliveryRequest();
		this.isDefault = dto.isDefault();
	}
}
