package com.practice.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.bank.domain.BankAccount;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceDto {

    @NotNull
    @Positive
    private Float balance;

    public static BalanceDto fromBankAccount(BankAccount bankAccount) {
        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setBalance(bankAccount.getBalance());

        return balanceDto;
    }
}
