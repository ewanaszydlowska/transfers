package com.imaginarycompany.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String accountNo;
    private BigDecimal balance;
}
