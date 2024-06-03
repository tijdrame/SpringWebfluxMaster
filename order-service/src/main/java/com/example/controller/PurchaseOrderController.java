package com.example.controller;

import com.example.dto.PurchaseOrderRequestDto;
import com.example.dto.PurchaseOrderResponseDto;
import com.example.service.OrderFullfillmentService;
import com.example.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class PurchaseOrderController {
    private final OrderFullfillmentService service;
    private final OrderQueryService orderQueryService;
    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(
            @RequestBody Mono<PurchaseOrderRequestDto> dto){
        return service.processOrder(dto)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class,
                        ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class,
                        ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }
    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrderByUserId(
            @PathVariable int userId){
        return orderQueryService.getProductsByUserId(userId);
    }
}
