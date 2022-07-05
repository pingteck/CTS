package com.pt.cts.util;

import com.pt.cts.entity.AggregatedPrice;
import com.pt.cts.entity.TradingPair;

public final class CTSUtil {

	public static AggregatedPrice getAggregatedBuyPrice(final TradingPair exchange1, final TradingPair exchange2) {
		if (exchange1.getBuyPrice() < exchange2.getBuyPrice()) {
			return new AggregatedPrice(exchange1.getExchange(), exchange1.getBuyPrice(), exchange1.getTimestamp());
		}
		return new AggregatedPrice(exchange2.getExchange(), exchange2.getBuyPrice(), exchange2.getTimestamp());
	}

	public static AggregatedPrice getAggregatedSellPrice(final TradingPair exchange1, final TradingPair exchange2) {
		if (exchange1.getSellPrice() > exchange2.getSellPrice()) {
			return new AggregatedPrice(exchange1.getExchange(), exchange1.getSellPrice(), exchange1.getTimestamp());
		}
		return new AggregatedPrice(exchange2.getExchange(), exchange2.getSellPrice(), exchange2.getTimestamp());
	}

	private CTSUtil() {
	}

}
