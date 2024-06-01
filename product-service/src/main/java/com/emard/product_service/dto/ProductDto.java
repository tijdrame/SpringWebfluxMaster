package com.emard.product_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private String id;
    private String description;
    private int price;
}
