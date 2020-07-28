package com.practice.bank.service;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.CurrencyType;
import com.practice.bank.repository.BankAccountRepository;
import com.practice.bank.repository.UserRepository;
import com.practice.bank.service.interfaces.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount getBankAccountByUserIdAndCurrencyType(Long userId, CurrencyType currencyType) {
        BankAccount result = bankAccountRepository.findBankAccountByUserIdAndCurrencyType(userId, currencyType);

        if (result == null) {
            log.warn("IN getBankAccountByUserIdAndCurrencyType - no bankAccount found by userId: {} and currencyType: {}", userId, currencyType);
            return null;
        }
        log.info("IN getBankAccountByUserIdAndCurrencyType - bankAccount: {}, found by userId: {} and currencyType: {}", result, userId, currencyType);

        return result;
    }

    @Override
    public List<BankAccount> getAllByUserId(Long id) {
        List<BankAccount> result = bankAccountRepository.findAllByUserId(id);

        if (result.isEmpty()) {
            log.warn("IN getAllByUserId - no bankAccounts found by userId: {}", id);
            return null;
        }
        log.info("IN getAllByUserId - bankAccounts: {}, found by userId: {}", result, id);

        return result;
    }

    @Override
    public BankAccount getOneById(Long id) {
        BankAccount result = bankAccountRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN getOneById - no bankAccount found by id: {}", id);
            return null;
        }
        log.info("IN getOneById - bankAccount: {}, found by id: {}", result, id);

        return result;
    }

    @Override
    public List<BankAccount> getAll() {
        List<BankAccount> result = bankAccountRepository.findAll();
        if (result.isEmpty())
            log.info("IN getAll - no bankAccounts found");
        log.info("IN getAll - {} bankAccounts found", result.size());
        return result;
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {
        User user = userRepository.getOne(bankAccount.getUser().getId());
        List<BankAccount> bankAccountList = user.getBankAccounts();
        bankAccountList.add(bankAccount);
        user.setBankAccounts(bankAccountList);
        userRepository.save(user);
        log.info("IN create - bankAccount with id: {}, was created", bankAccount.getId());
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount update(Long id, BankAccount bankAccount) {
        BankAccount bankAccountFromDb = bankAccountRepository.findById(id).orElse(null);

        if (bankAccountFromDb == null) {
            log.warn("IN update - no bankAccount found by id: {}", id);
            return null;
        }

        BeanUtils.copyProperties(bankAccount, bankAccountFromDb, "id");

        log.info("IN update - bankAccount: {} found by id: {} and updated successfully", bankAccount, id);

        return bankAccountRepository.save(bankAccountFromDb);
    }

    @Override
    public void delete(Long id) {
        BankAccount result = bankAccountRepository.findById(id).orElse(null);

        if (result == null)
            log.warn("IN delete - no bankAccount found by id: {}", id);

        bankAccountRepository.delete(result);
        log.info("IN delete - bankAccount with id: {} successfully deleted", id);
    }
}
