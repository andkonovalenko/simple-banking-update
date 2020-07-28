package com.practice.bank.service;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.Operation;
import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.CurrencyType;
import com.practice.bank.domain.enumerations.OperationType;
import com.practice.bank.domain.enumerations.UserStatus;
import com.practice.bank.dto.BalanceDto;
import com.practice.bank.dto.BankAccountDto;
import com.practice.bank.dto.ChangeBalanceDto;
import com.practice.bank.repository.UserRepository;
import com.practice.bank.service.interfaces.BankAccountService;
import com.practice.bank.service.interfaces.OperationService;
import com.practice.bank.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;
    private final PasswordEncoder passwordEncoder;
    private final OperationService operationService;

    @Autowired
    UserServiceImpl(UserRepository userRepository,
                    PasswordEncoder passwordEncoder,
                    BankAccountService bankAccountService,
                    OperationService operationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @Override
    public User create(User user) {
        log.info("IN create - user with id: {}, was created", user.getId());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        if (result.isEmpty())
            log.warn("IN getAll - no users found");

        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User getOneById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {}, found by id: {}", result, id);
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findUserByEmail(email);
        if (result == null)
            log.warn("IN findByEmail - no user found by email: {}", email);

        log.info("IN findByEmail - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    public User update(Long id, User user) {

        User userFromDb = userRepository.findById(id).orElse(null);

        if (userFromDb == null) {
            log.warn("IN update - no user found by id: {}", id);
            return null;
        }

        BeanUtils.copyProperties(user, userFromDb, "id");
        userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("IN update - user with id: {} was successfully updated", id);

        return userRepository.save(userFromDb);
    }

    @Override
    public void delete(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null)
            log.warn("IN delete - no user found by id: {}", id);

        result.setUserStatus(UserStatus.DELETE);
        userRepository.save(result);
        log.info("IN delete - user with id: {} successfully deleted", id);

    }

    @Override
    public boolean isExist(String userEmail) {

        User userFromDb = userRepository.findUserByEmail(userEmail);

        return userFromDb != null;
    }

    @Override
    public List<BankAccountDto> getUserAccounts(Long userId) {
        List<BankAccount> bankAccountList = bankAccountService.getAllByUserId(userId);

        if (bankAccountList.isEmpty())
            log.warn("IN getUserAccounts - no bankAccounts found");

        List<BankAccountDto> result = new ArrayList<>();
        bankAccountList.forEach(bankAccount -> result.add(BankAccountDto.fromBankAccount(bankAccount)));

        log.info("IN getUserAccounts - {} bankAccounts was found", result.size());
        return result;
    }

    @Override
    public BankAccountDto getUserBankAccount(Long userId, CurrencyType currencyType) {
        BankAccount bankAccount = bankAccountService.getBankAccountByUserIdAndCurrencyType(userId, currencyType);

        if (bankAccount == null)
            log.warn("IN getUserBankAccount - no bankAccount found by userId: {} and currencyType: {}", userId, currencyType);

        log.info("IN getUserBankAccount - bankAccount: {} found by userId: {} and currencyType: {}", bankAccount, userId, currencyType);
        return BankAccountDto.fromBankAccount(bankAccount);
    }

    @Override
    public BalanceDto getUserBalance(Long userId, CurrencyType currencyType) {

        BankAccount bankAccount = bankAccountService.getBankAccountByUserIdAndCurrencyType(userId, currencyType);

        if (bankAccount == null)
            log.warn("IN getUserBalance - no bankAccount found by userId: {} and currencyType: {}", userId, currencyType);

        log.info("IN getUserBalance - balance: {} found successfully", bankAccount.getBalance());
        return BalanceDto.fromBankAccount(bankAccount);
    }

    @Override
    public BankAccountDto changeUserBalance(Long userId, ChangeBalanceDto changeBalanceDto) {

        BankAccount bankAccount = bankAccountService.getBankAccountByUserIdAndCurrencyType(userId, changeBalanceDto.getCurrencyType());

        if (bankAccount == null)
            log.warn("IN changeUserBalance - no bankAccount found by userId: {} and currencyType: {}", userId, changeBalanceDto.getCurrencyType());

        BankAccountDto bankAccountDto = BankAccountDto.fromBankAccount(bankAccount);
        List<Operation> operationList = bankAccount.getOperations();
        Operation operation = new Operation();

        operation.setBankAccount(bankAccount);
        operation.setMoneyAmount(changeBalanceDto.getMoneyAmount());
        operation.setOperationDate(Instant.now());

        switch (changeBalanceDto.getOperationType()) {
            case DEPOSIT:
                operation.setOperationType(OperationType.DEPOSIT);
                bankAccountDto.setBalance(bankAccount.getBalance() + changeBalanceDto.getMoneyAmount());
                log.info("IN changeUserBalance - balance was successfully increased by {}", changeBalanceDto.getMoneyAmount());
            case WITHDRAW:
                operation.setOperationType(OperationType.WITHDRAW);
                bankAccountDto.setBalance(bankAccount.getBalance() - changeBalanceDto.getMoneyAmount());
                log.info("IN changeUserBalance - balance was successfully decreased by {}", changeBalanceDto.getMoneyAmount());
        }
        operationList.add(operation);
        bankAccountDto.setOperations(operationList);
        operationService.create(operation);
        bankAccountService.update(bankAccount.getId(), bankAccountDto.toBankAccount());

        return bankAccountDto;
    }
}
