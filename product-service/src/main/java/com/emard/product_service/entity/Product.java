package com.emard.product_service.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class Product {
    @Id
    private String id;
    private String description;
    private int price;
}
