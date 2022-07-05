package com.pt.cts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pt.cts.entity.AggregatedPrice;
import com.pt.cts.entity.BtcUsdt;
import com.pt.cts.entity.EthUsdt;
import com.pt.cts.repository.BtcUsdtRepository;
import com.pt.cts.repository.EthUsdtRepository;
import com.pt.cts.util.CTSUtil;
import com.pt.cts.util.Constants;

@RestController
@RequestMapping("/aggregated")
public class AggregatedPriceController {

	private final BtcUsdtRepository btcusdt;
	private final EthUsdtRepository ethusdt;

	public AggregatedPriceController(final BtcUsdtRepository btcusdt, final EthUsdtRepository ethusdt) {
		this.btcusdt = btcusdt;
		this.ethusdt = ethusdt;
	}

	@GetMapping("/ask/{tradingPair}")
	public AggregatedPrice ask(@PathVariable final String tradingPair) {
		if (tradingPair.equals(Constants.BTCUSDT)) {
			final BtcUsdt binance = this.btcusdt.getReferenceById(Constants.BINANCE);
			final BtcUsdt huobi = this.btcusdt.getReferenceById(Constants.HUOBI);
			return CTSUtil.getAggregatedBuyPrice(binance, huobi);
		}
		if (tradingPair.equals(Constants.ETHUSDT)) {
			final EthUsdt binance = this.ethusdt.getReferenceById(Constants.BINANCE);
			final EthUsdt huobi = this.ethusdt.getReferenceById(Constants.HUOBI);
			return CTSUtil.getAggregatedBuyPrice(binance, huobi);
		}
		return null;
	}

	@GetMapping("/bid/{tradingPair}")
	public AggregatedPrice bid(@PathVariable final String tradingPair) {
		if (tradingPair.equals(Constants.BTCUSDT)) {
			final BtcUsdt binance = this.btcusdt.getReferenceById(Constants.BINANCE);
			final BtcUsdt huobi = this.btcusdt.getReferenceById(Constants.HUOBI);
			return CTSUtil.getAggregatedSellPrice(binance, huobi);
		}
		if (tradingPair.equals(Constants.ETHUSDT)) {
			final EthUsdt binance = this.ethusdt.getReferenceById(Constants.BINANCE);
			final EthUsdt huobi = this.ethusdt.getReferenceById(Constants.HUOBI);
			return CTSUtil.getAggregatedSellPrice(binance, huobi);
		}
		return null;
	}

}
