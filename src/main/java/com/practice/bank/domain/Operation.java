package com.practice.bank.domain;

import com.practice.bank.domain.enumerations.OperationType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@Table(name = "operation")
public class Operation extends BaseEntity {

    @NotNull(message = "Choose operation type.")
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    @NotBlank(message = "Enter some sum of money.")
    @DecimalMin(value = "0.1", message = "Enter at least 0.1 sum.")
    @Column(name = "money_amount", nullable = false)
    private Float moneyAmount;

    @NotBlank(message = "Enter date of operation.")
    @Column(name = "date", nullable = false)
    private Instant operationDate;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;
}
