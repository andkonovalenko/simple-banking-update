package com.practice.bank.service.interfaces;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.Operation;

public interface OperationService extends  EntityCrudService<Operation> {
    Operation getOperationByBankAccount(BankAccount bankAccount);
}
