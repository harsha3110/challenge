package com.dws.challenge.repository;

import java.math.BigDecimal;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.MissingAccountException;
import com.dws.challenge.exception.NegativeAmountException;

public interface AccountsRepository {
  void createAccount(Account account) throws DuplicateAccountIdException;
  Account getAccount(String accountId);
  void clearAccounts();
  void transferMoney(Account accountFrom, Account accountTo, BigDecimal amount) throws MissingAccountException, NegativeAmountException;
}
