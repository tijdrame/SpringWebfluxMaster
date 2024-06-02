package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseOrderRequestDto {
    private Integer userId;
    private String productId;
}
