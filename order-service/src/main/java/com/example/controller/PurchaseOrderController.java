package com.example.controller;

import com.example.dto.PurchaseOrderRequestDto;
import com.example.dto.PurchaseOrderResponseDto;
import com.example.service.OrderFullfillmentService;
import com.example.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class PurchaseOrderController {
    private final OrderFullfillmentService service;
    private final OrderQueryService orderQueryService;
    @PostMapping
    public Mono<PurchaseOrderResponseDto> order(
            @RequestBody Mono<PurchaseOrderRequestDto> dto){
        return service.processOrder(dto);
    }
    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrderByUserId(
            @PathVariable int userId){
        return orderQueryService.getProductsByUserId(userId);
    }
}
