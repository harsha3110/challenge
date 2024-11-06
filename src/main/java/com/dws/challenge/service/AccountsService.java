package com.dws.challenge.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.MissingAccountException;
import com.dws.challenge.exception.NegativeAmountException;
import com.dws.challenge.repository.AccountsRepository;

import lombok.Getter;

@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public void transferMoney(Account accountFrom, Account accountTo, BigDecimal amount)
			throws MissingAccountException, NegativeAmountException {
		this.accountsRepository.transferMoney(accountFrom, accountTo, amount);
		
		emailNotificationService.notifyAboutTransfer(accountTo, "Amount successfully credited from account no: "
				+ accountFrom.getAccountId() + " with amount: " + amount + " the new balance is : " + accountTo.getBalance());

		emailNotificationService.notifyAboutTransfer(accountFrom, "Amount successfully debited from account no: "
				+ accountTo.getAccountId() + " with amount: " + amount + " the new balance is: " + accountFrom.getBalance());

	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}
}
