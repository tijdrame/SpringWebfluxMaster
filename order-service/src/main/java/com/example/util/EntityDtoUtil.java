package com.example.util;

import com.example.dto.OrderStatus;
import com.example.dto.PurchaseOrderResponseDto;
import com.example.dto.RequestContext;
import com.example.dto.TransactionRequestDto;
import com.example.dto.TransactionStatus;
import com.example.entity.PurchaseOrder;

import static com.example.dto.OrderStatus.COMPLETED;
import static com.example.dto.OrderStatus.FAILED;
import static com.example.dto.TransactionStatus.APPROVED;

public class EntityDtoUtil {
    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(
            PurchaseOrder order){
        return PurchaseOrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getAmount())
                .productId(order.getProductId())
                .status(order.getStatus())
                .build();
    }
    public static void setTransactionRequestDto(RequestContext rc){
        var dto =  TransactionRequestDto.builder()
                .userId(rc.getPurchaseOrderRequestDto().getUserId())
                .amount(rc.getProductDto().getPrice())
                .build();
        rc.setTransactionRequestDto(dto);
    }
    public static PurchaseOrder getPurchaseOrder(RequestContext rc){
        return PurchaseOrder.builder()
                .userId(rc.getPurchaseOrderRequestDto().getUserId())
                .productId(rc.getPurchaseOrderRequestDto().getProductId())
                .amount(rc.getProductDto().getPrice())
                .status(APPROVED.equals(rc.getTransactionResponseDto().getStatus())?
                        COMPLETED: FAILED)
                .build();
    }
}
