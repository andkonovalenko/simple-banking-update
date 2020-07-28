package com.practice.bank.repository;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    Operation findOperationByBankAccount(BankAccount bankAccount);
}
