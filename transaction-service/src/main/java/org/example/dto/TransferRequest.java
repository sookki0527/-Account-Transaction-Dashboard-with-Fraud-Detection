package org.example.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String userId;
    private Long fromAccountId;
    private Long toAccountId;
    private double amount;
    private String date;
}