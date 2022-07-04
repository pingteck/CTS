package com.pt.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pt.cts.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
