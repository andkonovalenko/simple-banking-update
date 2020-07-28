package com.practice.bank.web.rest;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.CurrencyType;
import com.practice.bank.dto.*;
import com.practice.bank.service.AuthenticationService;
import com.practice.bank.service.interfaces.BankAccountService;
import com.practice.bank.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user/")
public class UserRestController {

    private final UserService userService;
    private final BankAccountService bankAccountService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserRestController(UserService userService,
                              BankAccountService bankAccountService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.bankAccountService = bankAccountService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Long id) {
        User user = userService.getOneById(id);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<List<BankAccountDto>> getUserBankAccounts(@PathVariable(name = "id") Long id) {
        List<BankAccount> bankAccountList = bankAccountService.getAllByUserId(id);

        if (bankAccountList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(userService.getUserAccounts(id), HttpStatus.OK);
    }

    @GetMapping("{id}/{currency_type}")
    public ResponseEntity getOneUserBankAccount(@PathVariable(name = "id") Long id,
                                                @PathVariable(name = "currency_type") CurrencyType currencyType) {
        BankAccount bankAccount = bankAccountService.getBankAccountByUserIdAndCurrencyType(id, currencyType);

        if (bankAccount == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userService.getUserBankAccount(id, currencyType), HttpStatus.OK);
    }

    @GetMapping("{id}/{currency_type}/balance")
    public ResponseEntity<BalanceDto> getUserBankAccountBalance(@PathVariable(name = "id") Long id,
                                                                @PathVariable(name = "currency_type") CurrencyType currencyType) {
        BankAccount bankAccount = bankAccountService.getBankAccountByUserIdAndCurrencyType(id, currencyType);

        if (bankAccount == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userService.getUserBalance(id, currencyType), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PostMapping("{id}/transactions")
    public ResponseEntity<BankAccountDto> changeUserBankAccountBalance(@PathVariable(name = "id") Long id, @RequestBody @Valid ChangeBalanceDto changeBalanceDto) {
        BankAccount bankAccount = bankAccountService.getBankAccountByUserIdAndCurrencyType(id, changeBalanceDto.getCurrencyType());

        if (bankAccount == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userService.changeUserBalance(id, changeBalanceDto), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        User userFromDb = userService.getOneById(id);

        if (userFromDb == null && !userService.isExist(registrationRequestDto.getEmail())) {
            authenticationService.registration(registrationRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        User createdUser = new User();
        createdUser.setUserName(registrationRequestDto.getUserName());
        createdUser.setEmail(registrationRequestDto.getEmail());
        return new ResponseEntity<>(userService.update(id, createdUser), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        User userFromDb = userService.getOneById(id);

        if (userFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
