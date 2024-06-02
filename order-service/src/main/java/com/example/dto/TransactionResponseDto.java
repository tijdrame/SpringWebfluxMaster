package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionResponseDto {
    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}
