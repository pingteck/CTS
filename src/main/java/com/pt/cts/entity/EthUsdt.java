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
@Table(name = "ethusdt")
public class EthUsdt implements TradingPair, Serializable {

	private static final long serialVersionUID = -6986615523692481755L;

	@Id
	private String exchange;

	private double sellPrice;

	private double buyPrice;

	private Timestamp timestamp;

	protected EthUsdt() {
	}

}
