package com.emard.webfluxdemo.webclient;

import com.emard.webfluxdemo.dto.InputFailedValidationResponse;
import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {
    @Test
    void badRequestTest(){
        Mono<Object> response = webClient
                .get()
                .uri("reactive-math/square/{number}",
                        10)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err-> System.out.println(err.getMessage()));
        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

    }
    private Mono<Object> exchange(ClientResponse cr){
        if(cr.statusCode().value()==400){
            return cr.bodyToMono(InputFailedValidationResponse.class);
        }else
            return cr.bodyToMono(Response.class);
    }
}
