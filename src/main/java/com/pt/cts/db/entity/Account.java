package com.pt.cts.db.entity;

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

	/**
	 *
	 */
	private static final long serialVersionUID = -4281314094290724556L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false)
	private double usdt;

	@Column(nullable = false)
	private double btc;

	@Column(nullable = false)
	private double eth;

}
