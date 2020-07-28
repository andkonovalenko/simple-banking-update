package com.practice.bank.repository;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.enumerations.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    BankAccount findBankAccountByUserIdAndCurrencyType(Long id, CurrencyType currencyType);
    List<BankAccount> findAllByUserId(Long id);
}
