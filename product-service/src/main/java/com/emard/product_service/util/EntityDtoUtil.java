package com.emard.product_service.util;

import com.emard.product_service.dto.ProductDto;
import com.emard.product_service.entity.Product;

public class EntityDtoUtil {
    public static ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
    public static Product toEntity(ProductDto dto){
        return Product.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }
}
