package com.emard.util;

import com.emard.dto.TransactionRequestDto;
import com.emard.dto.TransactionResponseDto;
import com.emard.dto.TransactionStatus;
import com.emard.dto.UserDto;
import com.emard.entity.User;
import com.emard.entity.UserTransaction;

import java.time.LocalDateTime;

public class EntityDtoUtil {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .balance(user.getBalance())
                .build();
    }

    public static User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .balance(dto.getBalance()).build();
    }
    public static UserTransaction toEntity(TransactionRequestDto dto){
        return UserTransaction.builder()
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .transactionDate(LocalDateTime.now())
                .build();
    }
    public static TransactionResponseDto toDto(TransactionRequestDto  dto,
                                               TransactionStatus status){
        return TransactionResponseDto.builder()
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .status(status)
                .build();
    }
}
