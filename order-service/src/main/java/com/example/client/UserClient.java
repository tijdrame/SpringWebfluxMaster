package com.example.client;

import com.example.dto.TransactionRequestDto;
import com.example.dto.TransactionResponseDto;
import com.example.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private final WebClient webClient;
    public UserClient(@Value("${user.service.url}") String url){
        webClient = WebClient.builder().baseUrl(url).build();
    }
    public Mono<TransactionResponseDto> authorizeTransaction(
            TransactionRequestDto dto){
        return webClient.post().uri("transaction")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }
    public Flux<UserDto> getAllUsers(){
        return webClient.get()
                .uri("all")
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}
