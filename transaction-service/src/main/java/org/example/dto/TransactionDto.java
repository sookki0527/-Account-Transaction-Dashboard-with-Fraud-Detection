package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long id;
    private String userId;
    private Long fromAccountId;
    private Long toAccountId;
   private double amount;
    private Type type;
    private String date;
}
