package com.emard.controller;

import com.emard.dto.TransactionRequestDto;
import com.emard.dto.TransactionResponseDto;
import com.emard.entity.UserTransaction;
import com.emard.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/transaction")
public class TransactionController {
    private final TransactionService service;
    @PostMapping
    public Mono<TransactionResponseDto> createtransaction(
            @RequestBody Mono<TransactionRequestDto> dto){
        return dto.flatMap(service::createTransaction);
    }
    @GetMapping("{userId}")
    public Flux<UserTransaction> getByUserId(@PathVariable int userId){
        return service.getByUserId(userId);
    }
}
