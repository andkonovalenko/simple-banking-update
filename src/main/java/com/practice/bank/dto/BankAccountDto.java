package com.practice.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.Operation;
import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.CurrencyType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankAccountDto {

    @NotNull
    private Long id;

    @NotBlank
    @Positive
    private Float balance;

    @NotBlank
    private Instant dateOfCreation;

    @NotNull
    private CurrencyType currencyType;

    @NotNull
    private User user;

    @NotNull
    private List<Operation> operations;

    public BankAccount toBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setBalance(balance);
        bankAccount.setDateOfCreation(dateOfCreation);
        bankAccount.setCurrencyType(currencyType);
        bankAccount.setUser(user);

        return bankAccount;
    }

    public static BankAccountDto fromBankAccount(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(bankAccount.getId());
        bankAccountDto.setBalance(bankAccount.getBalance());
        bankAccountDto.setDateOfCreation(bankAccount.getDateOfCreation());
        bankAccountDto.setCurrencyType(bankAccount.getCurrencyType());
        bankAccountDto.setUser(bankAccount.getUser());

        return bankAccountDto;
    }
}
