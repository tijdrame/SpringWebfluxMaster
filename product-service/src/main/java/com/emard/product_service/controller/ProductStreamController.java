package com.emard.product_service.controller;

import com.emard.product_service.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("product")
public class ProductStreamController {
    private final Flux<ProductDto> flux;
    @GetMapping(value = "stream/{maxPrice}", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductUpdates(@PathVariable int maxPrice){
        return flux.filter(dto->dto.getPrice() <= maxPrice);
    }
}
