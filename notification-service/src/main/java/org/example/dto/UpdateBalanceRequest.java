package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateBalanceRequest {
    private Double balance;
    private String type;
    private Long accountId;
    private String userId;
    private LocalDateTime date;
}
