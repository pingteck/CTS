package com.pt.cts.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pt.cts.entity.Account;
import com.pt.cts.entity.Balance;
import com.pt.cts.repository.AccountRepository;

@RestController
public class AccountsController {

	private final AccountRepository accounts;

	public AccountsController(final AccountRepository accounts) {
		this.accounts = accounts;
	}

	@GetMapping("/accounts")
	List<Account> accounts() {
		return this.accounts.findAll();
	}

	@GetMapping("/accounts/{id}")
	Account getAccount(@PathVariable final Long id) {
		return this.accounts.findById(id).orElseThrow();
	}

	@GetMapping("/accounts/{id}/balance")
	Balance getBalance(@PathVariable final Long id) {
		final Account account = this.accounts.findById(id).orElseThrow();
		return new Balance(account.getUsdt(), account.getBtc(), account.getEth());
	}

}
