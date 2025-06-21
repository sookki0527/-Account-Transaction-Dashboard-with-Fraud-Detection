package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long accountId;

    private String userId;
    private String accountType;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
}
