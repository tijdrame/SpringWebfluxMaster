package com.emard.webfluxdemo;

import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05BadRequestTest extends BaseTest {
    @Test
    void badRequestTest(){
        Mono<Response> response = webClient
                .get()
                .uri("reactive-math/square/{number}",
                        5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(err-> System.out.println(err.getMessage()));
        StepVerifier.create(response)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
