package com.pt.cts.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.pt.cts.entity.EthUsdt;

@Repository
public interface EthUsdtRepository extends JpaRepositoryImplementation<EthUsdt, String> {

	@Transactional
	@Modifying
	@Query("update EthUsdt set buy_price = ?1 , sell_price = ?2 , timestamp = CURRENT_TIMESTAMP where exchange = ?3")
	int updateEthUsdt(double buyPrice, double sellPrice, String exchange);

}
