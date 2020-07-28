package com.practice.bank.domain;


import com.practice.bank.domain.enumerations.CurrencyType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "bank_account")
public class BankAccount extends BaseEntity{

    @DecimalMin( value = "0.00")
    @Column(name = "balance", nullable = false)
    private Float balance;

    @NotBlank(message = "Set date of creation")
    @Column(name = "date_of_creation", nullable = false)
    private Instant dateOfCreation;

    @NotNull(message = "Choose currency type.")
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

    @ManyToOne
    @JoinColumn(name = "bank_user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operations;
}
