package com.emard.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionRequestDto {
    private Integer userId;
    private Integer amount;
}
