package com.pt.cts.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pt.cts.entity.TradeHistory;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {

	@Transactional
	@Modifying
	@Query(value = "insert into trade_history (account_id, exchange, ticker, price, buy_amount) "
			+ "values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	int buy(long accountId, String exchange, String ticker, double price, double buyAmount);

	@Transactional
	@Modifying
	@Query(value = "insert into trade_history (account_id, exchange, ticker, price, sell_amount) "
			+ "values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	int sell(long accountId, String exchange, String ticker, double price, double sellAmount);

}
