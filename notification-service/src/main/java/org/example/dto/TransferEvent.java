package org.example.dto;

import lombok.Data;

@Data
public class TransferEvent {
    private String fromAccountId;
    private String toAccountId;
    private double amount;
}
