package com.pt.cts.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = -4281314094290724556L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "usdt", nullable = false, columnDefinition = "double default 0")
	private double usdt;

	@Column(name = "btc", nullable = false, columnDefinition = "double default 0")
	private double btc;

	@Column(name = "eth", nullable = false, columnDefinition = "double default 0")
	private double eth;

	protected Account() {
	}

	public Account(final String name) {
		this.name = name;
	}

}
