package com.pt.cts.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AggregatedPrice {

	private String exchange;

	private double price;

	private Timestamp timestamp;

}
