package com.pt.cts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Balance {

	private final double usdt;
	private final double btc;
	private final double eth;

}
