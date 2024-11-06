package com.dws.challenge.domain;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountTransfer {
	@NotNull
	Account accountFrom;
	@NotNull
	Account accountTo;
	@NotNull
	@Min(value = 0, message = "Initial balance must be positive.")
	BigDecimal amount;
	
	public AccountTransfer () {}
	
	public AccountTransfer (Account accountFrom, Account accountTo, BigDecimal amount) {
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}
}
