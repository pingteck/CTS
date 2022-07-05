package com.pt.cts.entity;

import java.sql.Timestamp;

public interface TradingPair {

	String getExchange();

	double getBuyPrice();

	double getSellPrice();

	Timestamp getTimestamp();

}
