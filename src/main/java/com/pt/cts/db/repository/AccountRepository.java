package com.pt.cts.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pt.cts.db.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
