package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private String id;

    private String userId;
    private String accountType;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
}