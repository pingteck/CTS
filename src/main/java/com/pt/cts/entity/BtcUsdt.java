package com.pt.cts.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "btcusdt")
public class BtcUsdt implements TradingPair, Serializable {

	private static final long serialVersionUID = -2979878060912029192L;

	@Id
	private String exchange;

	private double sellPrice;

	private double buyPrice;

	private Timestamp timestamp;

	protected BtcUsdt() {
	}

}
