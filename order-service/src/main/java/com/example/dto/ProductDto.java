package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private String id;
    private String description;
    private int price;
}
