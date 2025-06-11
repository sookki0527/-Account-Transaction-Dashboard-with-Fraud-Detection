package org.example.dto;

import lombok.Data;

@Data
public class Account {

    private String id;

    private String userId;
    private String accountType;
    private String accountNumber;
    private Double balance;
    private String currency;
}