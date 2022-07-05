package com.pt.cts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pt.cts.entity.Account;
import com.pt.cts.entity.AggregatedPrice;
import com.pt.cts.entity.Trade;
import com.pt.cts.entity.TradeHistory;
import com.pt.cts.repository.AccountRepository;
import com.pt.cts.repository.TradeHistoryRepository;
import com.pt.cts.util.Constants;

@RestController
@RequestMapping("/trade")
public class TradeController {

	private final TradeHistoryRepository tradeHistory;
	private final AggregatedPriceController aggregatedPriceController;
	private final AccountRepository accounts;

	private static final String BAD_BALANCE = "Not enough balance";
	private static final String BAD_TRADING_PAIR = "No such trading pair";

	public TradeController(final TradeHistoryRepository tradeHistory,
			final AggregatedPriceController aggregatedPriceController, final AccountRepository accounts) {
		this.tradeHistory = tradeHistory;
		this.aggregatedPriceController = aggregatedPriceController;
		this.accounts = accounts;
	}

	// 5. Create an api to retrieve the user trading history.
	// TODO: check for authorisation
	@GetMapping("/{accountId}/history")
	public List<TradeHistory> getTradeHistory(@PathVariable final Long accountId) {
		return this.tradeHistory.findByAccountId(accountId);
	}

	// 3. Create an api which allows users to trade based on the latest best
	// aggregated price.
	// Remarks: Do not integrate with other third party system

	// TODO: check for authorisation
	// TODO: clean up this whole part?

	@PostMapping(path = "/{accountId}/buy")
	@ResponseBody
	public TradeHistory buy(@PathVariable final Long accountId, @RequestBody final Trade trade) {
		final Account account = this.accounts.getReferenceById(accountId);
		if (trade.getTradingPair().equals(Constants.BTCUSDT)) {
			final AggregatedPrice aggregated = this.aggregatedPriceController.ask(Constants.BTCUSDT);
			if (!this.checkIfBalanceAvailableToBuy(account.getUsdt(), aggregated.getPrice(), trade.getAmount())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_BALANCE);
			}
			account.setUsdt(account.getUsdt() - (aggregated.getPrice() * trade.getAmount()));
			account.setBtc(account.getBtc() + trade.getAmount());
			final TradeHistory history = new TradeHistory(accountId, aggregated.getExchange(), trade.getTradingPair(),
					aggregated.getPrice(), trade.getAmount(), 0);
			return this.tradeHistory.save(history);
		}
		if (trade.getTradingPair().equals(Constants.ETHUSDT)) {
			final AggregatedPrice aggregated = this.aggregatedPriceController.ask(Constants.ETHUSDT);
			if (!this.checkIfBalanceAvailableToBuy(account.getUsdt(), aggregated.getPrice(), trade.getAmount())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_BALANCE);
			}
			account.setUsdt(account.getUsdt() - (aggregated.getPrice() * trade.getAmount()));
			account.setEth(account.getEth() + trade.getAmount());
			final TradeHistory history = new TradeHistory(accountId, aggregated.getExchange(), trade.getTradingPair(),
					aggregated.getPrice(), trade.getAmount(), 0);
			return this.tradeHistory.save(history);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_TRADING_PAIR);
	}

	@PostMapping(path = "/{accountId}/sell")
	@ResponseBody
	public TradeHistory sell(@PathVariable final Long accountId, @RequestBody final Trade trade) {
		final Account account = this.accounts.getReferenceById(accountId);
		if (trade.getTradingPair().equals(Constants.BTCUSDT)) {
			final AggregatedPrice aggregated = this.aggregatedPriceController.bid(Constants.BTCUSDT);
			if (!this.checkIfBalanceAvailableToSell(account.getBtc(), aggregated.getPrice(), trade.getAmount())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_BALANCE);
			}
			account.setUsdt(account.getUsdt() + (aggregated.getPrice() * trade.getAmount()));
			account.setBtc(account.getBtc() - trade.getAmount());
			final TradeHistory history = new TradeHistory(accountId, aggregated.getExchange(), trade.getTradingPair(),
					aggregated.getPrice(), trade.getAmount(), 0);
			return this.tradeHistory.save(history);
		}
		if (trade.getTradingPair().equals(Constants.ETHUSDT)) {
			final AggregatedPrice aggregated = this.aggregatedPriceController.bid(Constants.ETHUSDT);
			if (!this.checkIfBalanceAvailableToSell(account.getEth(), aggregated.getPrice(), trade.getAmount())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_BALANCE);
			}
			account.setUsdt(account.getUsdt() + (aggregated.getPrice() * trade.getAmount()));
			account.setEth(account.getEth() - trade.getAmount());
			final TradeHistory history = new TradeHistory(accountId, aggregated.getExchange(), trade.getTradingPair(),
					aggregated.getPrice(), trade.getAmount(), 0);
			return this.tradeHistory.save(history);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_TRADING_PAIR);
	}

	private boolean checkIfBalanceAvailableToBuy(final double balance, final double price, final double amount) {
		return balance > (price * amount);
	}

	private boolean checkIfBalanceAvailableToSell(final double balance, final double price, final double amount) {
		return amount > (balance / price);
	}

}
