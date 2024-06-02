package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseOrderResponseDto {
    private Integer orderId;
    private Integer userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}
