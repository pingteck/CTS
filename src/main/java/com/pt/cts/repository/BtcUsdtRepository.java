package com.pt.cts.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pt.cts.entity.BtcUsdt;

@Repository
public interface BtcUsdtRepository extends JpaRepository<BtcUsdt, String> {

	@Transactional
	@Modifying
	@Query("update BtcUsdt set buy_price = ?1 , sell_price = ?2 , timestamp = CURRENT_TIMESTAMP where exchange = ?3")
	int updateBtcUsdt(double buyPrice, double sellPrice, String exchange);

}
