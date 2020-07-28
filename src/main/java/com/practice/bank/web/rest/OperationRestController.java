package com.practice.bank.web.rest;

import com.practice.bank.domain.Operation;
import com.practice.bank.service.interfaces.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/operation/")
public class OperationRestController {

    private final OperationService operationService;

    @Autowired
    public OperationRestController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Operation> getOneOperation(@PathVariable("id") Long id) {
        Operation operationFromDb = operationService.getOneById(id);

        if (operationFromDb == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(operationFromDb, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> operationFromDbList = operationService.getAll();

        if (operationFromDbList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(operationFromDbList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Operation> createOperation(@RequestBody @Valid Operation operation) {
            return new ResponseEntity<>(operationService.create(operation), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Operation> updateOperation(@PathVariable("id") Long id, @RequestBody @Valid Operation operation) {
        Operation operationFromDb = operationService.getOneById(id);

        if (operationFromDb == null)
            return new ResponseEntity<>(operationService.create(operation), HttpStatus.CREATED);

        return new ResponseEntity<>(operationService.update(id, operation), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteOperation(@PathVariable("id") Long id) {
        Operation operationFromDb = operationService.getOneById(id);

        if (operationFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        operationService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
