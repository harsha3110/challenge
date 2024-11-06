package com.dws.challenge.domain;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountTransfer {
	@NotNull
	String accountIdFrom;
	@NotNull
	String accountIdTo;
	@NotNull
	@Min(value = 0, message = "Initial balance must be positive.")
	BigDecimal amount;
	
	public AccountTransfer () {}
	
	public AccountTransfer (String accountIdFrom, String accountIdTo, BigDecimal amount) {
		this.accountIdFrom = accountIdFrom;
		this.accountIdTo = accountIdTo;
		this.amount = amount;
	}
}
