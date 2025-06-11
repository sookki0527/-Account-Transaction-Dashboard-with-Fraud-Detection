package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long accountId;

    private String userId;
    private String accountType;
    private String accountNumber;
    private Double balance;
    private String currency;
}
