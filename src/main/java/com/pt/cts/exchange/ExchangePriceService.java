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

import com.pt.cts.db.DatabaseService;

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

	private final DatabaseService databaseService;

	protected ExchangePriceService(final DatabaseService databaseService) {
		this.databaseService = databaseService;
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
				if (this.checkTicker(symbol)) {
					final double bidPrice = object.getDouble("bidPrice");
					final double askPrice = object.getDouble("askPrice");
					final StringBuilder sb = new StringBuilder("update ");
					sb.append(symbol);
					sb.append(" set buy_price = ");
					sb.append(Double.toString(askPrice));
					sb.append(",  sell_price = ");
					sb.append(Double.toString(bidPrice));
					sb.append(", timestamp = CURRENT_TIMESTAMP where exchange = 'binance';");
					this.databaseService.executeStatement(sb.toString());
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
				if (this.checkTicker(symbol)) {
					final double bid = object.getDouble("bid");
					final double ask = object.getDouble("ask");
					final StringBuilder sb = new StringBuilder("update ");
					sb.append(symbol);
					sb.append(" set buy_price = ");
					sb.append(Double.toString(ask));
					sb.append(",  sell_price = ");
					sb.append(Double.toString(bid));
					sb.append(", timestamp = CURRENT_TIMESTAMP where exchange = 'huobi';");
					this.databaseService.executeStatement(sb.toString());
				}
			}
		} catch (final JSONException e) {
			log.error(e.toString());
		}
		return null;
	}

	private boolean checkTicker(final String ticker) {
		return ticker.equalsIgnoreCase("btcusdt") || ticker.equalsIgnoreCase("ethusdt");
	}

}
