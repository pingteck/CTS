package com.pt.cts.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pt.cts.entity.TradeHistory;
import com.pt.cts.repository.TradeHistoryRepository;

@RestController
@RequestMapping("/trade")
public class TradeController {

	private final TradeHistoryRepository tradeHistory;

	public TradeController(final TradeHistoryRepository tradeHistory) {
		this.tradeHistory = tradeHistory;
	}

	// 5. Create an api to retrieve the user trading history.
	// TODO: check for authorisation
	@GetMapping("/{accountId}/history")
	public List<TradeHistory> getTradeHistory(@PathVariable final Long accountId) {
		return this.tradeHistory.findByAccountId(accountId);
	}

}
