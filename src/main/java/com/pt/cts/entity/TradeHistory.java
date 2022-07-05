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
@Table(name = "trade_history")
public class TradeHistory implements Serializable {

	private static final long serialVersionUID = -3731147388093711861L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long accountId;

	private String exchange;

	private String ticker;

	private double price;

	@Column(columnDefinition = "double default 0")
	private double buyAmount;

	@Column(columnDefinition = "double default 0")
	private double sellAmount;

	protected TradeHistory() {
	}

}
