package com.practice.bank.service.interfaces;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.enumerations.CurrencyType;

import java.util.List;

public interface BankAccountService extends EntityCrudService<BankAccount> {

    BankAccount getBankAccountByUserIdAndCurrencyType(Long id, CurrencyType currencyType);

    List<BankAccount> getAllByUserId(Long id);
}
