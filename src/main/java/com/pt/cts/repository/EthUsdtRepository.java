package com.pt.cts.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.pt.cts.entity.EthUsdt;

@Repository
public interface EthUsdtRepository extends JpaRepositoryImplementation<EthUsdt, String> {

}
