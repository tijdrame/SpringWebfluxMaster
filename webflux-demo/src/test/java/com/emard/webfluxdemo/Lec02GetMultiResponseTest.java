package com.emard.webfluxdemo;

import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02GetMultiResponseTest extends BaseTest{
    @Test
    void fluxTest() {
        Flux<Response> responseFlux = webClient
                .get()
                .uri("reactive-math/table/{number}",
                        10)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
    @Test
    void fluxStreamTest() {
        Flux<Response> responseFlux = webClient
                .get()
                .uri("reactive-math/table/{number}/stream",
                        10)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
