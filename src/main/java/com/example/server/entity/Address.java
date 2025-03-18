package com.example.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "address", schema = "test1")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id", nullable = false)
	private Long id;

	private String realName;

	private String phoneNumber;

	private String address;

	private String postalCode;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	protected Address() {
	}

	public Address(User user, String realName, String phoneNumber, String address, String postalCode) {
		this.user = user;
		this.realName = realName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
}
