package com.dws.challenge.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.MissingAccountException;
import com.dws.challenge.exception.NegativeAmountException;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	public void transferMoney(Account accountFrom, Account accountTo, BigDecimal amount)
			throws MissingAccountException, NegativeAmountException {
		// check if amount is not negative or zero
		if (!(amount.compareTo(BigDecimal.ZERO) >= 1)) {
			throw new NegativeAmountException("Amount cannot be negative");
		}

		// check if current account in debit account is not less than amount to be
		// transfered
		synchronized (accountFrom) {
    		if (!((accountFrom.getBalance().compareTo(amount) >= 0))) {
	    		throw new NegativeAmountException("Overdraft not available");
		    }
    		synchronized (accountTo) {
    			// Check the previous balance
    			BigDecimal previousBalance = accountTo.getBalance();

    		    BigDecimal newBalanceinCreditAccount = updateAccountBalance(accountFrom, accountTo, amount);
    			if (newBalanceinCreditAccount.compareTo(previousBalance) >= 1) {

    				// if the balance of the credit account is increased then transfer is successful
    				log.info("Account transfer successful, the new balance in to account is:"
    						+ getAccount(accountTo.getAccountId()).getBalance());
    			}

    		}
	    }
	}

	private BigDecimal updateAccountBalance(Account accountFrom, Account accountTo, BigDecimal amount) {
		// Subtract from the debit account
		BigDecimal newAmountAfterDebit = accountFrom.getBalance().subtract(amount);
		accountFrom.setBalance(newAmountAfterDebit);
		// update the account
		accounts.replace(accountFrom.getAccountId(), accountFrom);

		// credit amount to the credit account
		BigDecimal newAmountAfterCredit = accountTo.getBalance().add(amount);
		accountTo.setBalance(newAmountAfterCredit);
		// update the account
		accounts.replace(accountTo.getAccountId(), accountTo);
		return accountTo.getBalance();
	}

}
