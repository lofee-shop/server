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
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address", schema = "test1")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Long id;

	@Column(name = "real_name", nullable = true)
	private String realName;

	@Column(name = "phone_number", nullable = true)
	private String phoneNumber;

	@Column(name = "address", nullable = true)
	private String address;

	@Column(name = "postal_code", nullable = true)
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
