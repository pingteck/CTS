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

	// TODO: user should not have access to this
	@GetMapping("/accounts")
	List<Account> accounts() {
		return this.accounts.findAll();
	}

	// TODO: check for authorisation
	@GetMapping("/accounts/{id}")
	Account getAccount(@PathVariable final Long id) {
		return this.accounts.findById(id).orElseThrow();
	}

	// 4. Create an api to retrieve the user’s crypto currencies wallet balance
	@GetMapping("/accounts/{id}/balance")
	Balance getBalance(@PathVariable final Long id) {
		final Account account = this.accounts.findById(id).orElseThrow();
		return new Balance(account.getUsdt(), account.getBtc(), account.getEth());
	}

}
