package com.practice.bank.service;

import com.practice.bank.domain.BankAccount;
import com.practice.bank.domain.Operation;
import com.practice.bank.repository.OperationRepository;
import com.practice.bank.service.interfaces.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation getOneById(Long id) {
        Operation operation = operationRepository.findById(id).orElse(null);

        if (operation == null) {
            log.warn("IN findById - no operation found by id: {}", id);
            return null;
        }
        log.info("IN findById - operation: {}, found by id: {}", operation, id);

        return operation;
    }

    @Override
    public List<Operation> getAll() {
        List<Operation> result = operationRepository.findAll();

        if (result.isEmpty())
            log.warn("IN getAll - no operations found");
        log.info("IN getAll - {} operations found", result.size());
        return result;
    }

    @Override
    public Operation create(Operation operation) {
        log.info("IN create - operation with id: {}, was created", operation.getId());
        return operationRepository.save(operation);
    }

    @Override
    public Operation update(Long id, Operation operation) {
        Operation operationFromDb = operationRepository.findById(id).orElse(null);

        if (operationFromDb == null) {
            log.warn("IN update - no operation found by id: {}", id);
            return null;
        }

        BeanUtils.copyProperties(operation, operationFromDb, "id");

        log.info("IN update - operation: {} found by id: {} and updated", operation, id);

        return operationRepository.save(operationFromDb);
    }

    @Override
    public void delete(Long id) {
        Operation result = operationRepository.findById(id).orElse(null);

        if (result == null)
            log.warn("IN delete - no operation found by id: {}", id);

        operationRepository.delete(result);
        log.info("IN delete - operation with id: {} successfully deleted", id);
    }

    @Override
    public Operation getOperationByBankAccount(BankAccount bankAccount) {
        Operation result = operationRepository.findOperationByBankAccount(bankAccount);

        if (result == null) {
            log.warn("IN getOperationByBankAccount - no operations found");
            return null;
        }
        log.info("IN getOperationByBankAccount - operation: {}, found by bankAccount: {}", result, bankAccount);
        return result;
    }
}
