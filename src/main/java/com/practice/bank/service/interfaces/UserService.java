package com.practice.bank.service.interfaces;

import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.CurrencyType;
import com.practice.bank.dto.BalanceDto;
import com.practice.bank.dto.BankAccountDto;
import com.practice.bank.dto.ChangeBalanceDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService extends EntityCrudService<User> {

    User findByEmail(String email);

    boolean isExist(String email);

    List<BankAccountDto> getUserAccounts(Long userId);

    BankAccountDto getUserBankAccount(Long userId, CurrencyType currencyType);

    BalanceDto getUserBalance(Long userId, CurrencyType currencyType);

    BankAccountDto changeUserBalance(Long userId, ChangeBalanceDto changeBalanceDto);
}
