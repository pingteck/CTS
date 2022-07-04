package com.pt.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pt.cts.entity.BtcUsdt;

@Repository
public interface BtcUsdtRepository extends JpaRepository<BtcUsdt, String> {

}
