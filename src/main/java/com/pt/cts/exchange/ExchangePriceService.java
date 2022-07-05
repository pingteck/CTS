package com.pt.cts.exchange;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pt.cts.Constants;
import com.pt.cts.repository.BtcUsdtRepository;
import com.pt.cts.repository.EthUsdtRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExchangePriceService {

	@Value("${cts.binance.url}")
	private String binanceUrl;

	@Value("${cts.huobi.url}")
	private String huobiUrl;

	private final HttpClient client;
	private HttpRequest binanceRequest;
	private HttpRequest huobiRequest;

	private final BtcUsdtRepository btcusdt;
	private final EthUsdtRepository ethusdt;

	protected ExchangePriceService(final BtcUsdtRepository btcusdt, final EthUsdtRepository ethusdt) {
		this.btcusdt = btcusdt;
		this.ethusdt = ethusdt;
		this.client = HttpClient.newHttpClient();
	}

	@PostConstruct
	private void init() {
		this.binanceRequest = HttpRequest.newBuilder(URI.create(this.binanceUrl)).header("accept", "application/json")
				.build();
		this.huobiRequest = HttpRequest.newBuilder(URI.create(this.huobiUrl)).header("accept", "application/json")
				.build();
	}

	@Scheduled(cron = "${cts.retrieval.interval}")
	protected void scheduledFetch() {
		if (this.binanceRequest != null) {
			this.client.sendAsync(this.binanceRequest, BodyHandlers.ofString()).thenApply(HttpResponse::body)
					.thenApply(this::parseBinance).join();
		}
		if (this.huobiRequest != null) {
			this.client.sendAsync(this.huobiRequest, BodyHandlers.ofString()).thenApply(HttpResponse::body)
					.thenApply(this::parseHuobi).join();
		}
	}

	/*
	 * Response from Binance: [{"symbol": string, "bidPrice": double, "bidQty":
	 * double, "askPrice": double, "askQty": double}]
	 */
	protected String parseBinance(final String responseBody) {
		JSONArray array;
		try {
			array = new JSONArray(responseBody);
			for (int i = 0; i < array.length(); i++) {
				final JSONObject object = array.getJSONObject(i);
				final String symbol = object.getString("symbol").toLowerCase();
				final double bidPrice = object.getDouble("bidPrice");
				final double askPrice = object.getDouble("askPrice");
				if (symbol.equals(Constants.BTCUSDT)) {
					this.btcusdt.updateBtcUsdt(askPrice, bidPrice, Constants.BINANCE);
				} else if (symbol.equals(Constants.ETHUSDT)) {
					this.ethusdt.updateEthUsdt(askPrice, bidPrice, Constants.BINANCE);
				}
			}
		} catch (final JSONException e) {
			log.error(e.toString());
		}
		return null;
	}

	/*
	 * Response from Huobi: "data": [{ "symbol": string, "open": double, "high":
	 * double, "low": double, "close": double, "amount": double, "vol": double,
	 * "count": int/double, "bid": double, "bidSize": double, "ask": double,
	 * "askSize": double, }]
	 */
	protected String parseHuobi(final String responseBody) {
		try {
			final JSONObject response = new JSONObject(responseBody);
			final JSONArray array = response.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				final JSONObject object = array.getJSONObject(i);
				final String symbol = object.getString("symbol").toLowerCase();
				final double bid = object.getDouble("bid");
				final double ask = object.getDouble("ask");
				if (symbol.equals(Constants.BTCUSDT)) {
					this.btcusdt.updateBtcUsdt(bid, bid, Constants.HUOBI);
				} else if (symbol.equals(Constants.ETHUSDT)) {
					this.ethusdt.updateEthUsdt(ask, bid, Constants.HUOBI);
				}
			}
		} catch (final JSONException e) {
			log.error(e.toString());
		}
		return null;
	}

}
