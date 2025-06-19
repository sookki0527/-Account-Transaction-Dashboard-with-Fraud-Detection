package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateBalanceRequest {
    private BigDecimal balance;
    private String type;
    private Long accountId;
    private String userId;
    private LocalDateTime date;
}
