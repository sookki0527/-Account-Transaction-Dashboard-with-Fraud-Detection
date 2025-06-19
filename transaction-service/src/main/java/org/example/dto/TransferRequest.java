package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransferRequest {
    private String userId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private String type;
    private LocalDateTime date;
}