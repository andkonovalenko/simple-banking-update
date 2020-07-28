package com.practice.bank.web.rest;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.service.interfaces.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/bank-account/")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("{id}")
    public ResponseEntity<BankAccount> getOneBankAccount(@PathVariable("id") Long id) {
        BankAccount bankAccountFromDb = bankAccountService.getOneById(id);

        if (bankAccountFromDb == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(bankAccountFromDb, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        List<BankAccount> bankAccountFromDbList = bankAccountService.getAll();

        if (bankAccountFromDbList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(bankAccountFromDbList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody @Valid BankAccount bankAccount) {
        return new ResponseEntity<>(bankAccountService.create(bankAccount), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable("id") Long id, @RequestBody @Valid BankAccount bankAccount) {
        BankAccount bankAccountFromDb = bankAccountService.getOneById(id);

        if (bankAccountFromDb == null)
            return new ResponseEntity<>(bankAccountService.create(bankAccount), HttpStatus.CREATED);

        return new ResponseEntity<>(bankAccountService.update(id, bankAccount), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BankAccount> deleteBankAccount(@PathVariable("id") Long id) {
        BankAccount bankAccountFromDb = bankAccountService.getOneById(id);

        if (bankAccountFromDb == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        bankAccountService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


