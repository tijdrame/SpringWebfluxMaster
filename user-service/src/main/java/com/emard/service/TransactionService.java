package com.emard.service;

import com.emard.dto.TransactionRequestDto;
import com.emard.dto.TransactionResponseDto;
import com.emard.dto.TransactionStatus;
import com.emard.entity.UserTransaction;
import com.emard.repo.UserRepository;
import com.emard.repo.UserTransactionRepository;
import com.emard.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.emard.dto.TransactionStatus.APPROVED;
import static com.emard.dto.TransactionStatus.DECLINED;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final UserTransactionRepository transactionRepository;
    public Mono<TransactionResponseDto>createTransaction(final TransactionRequestDto dto){
        return userRepository.updateUserBalance(dto.getUserId(), dto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b-> EntityDtoUtil.toEntity(dto))
                .flatMap(transactionRepository::save)
                .map(ut-> EntityDtoUtil.toDto(dto, APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(dto, DECLINED));
    }
    public Flux<UserTransaction> getByUserId(int userId){
        return transactionRepository.findByUserId(userId);
    }

}
