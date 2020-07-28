package com.practice.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.bank.domain.enumerations.CurrencyType;
import com.practice.bank.domain.enumerations.OperationType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeBalanceDto {

    @NotNull
    private CurrencyType currencyType;

    @NotNull
    private OperationType operationType;

    @Positive
    @NotNull
    private Float moneyAmount;

}
