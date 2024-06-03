package com.emard.webfluxdemo.webclient;

import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {
    @Test
    void blockTest() {
        Response response = webClient
                .get()
                .uri("reactive-math/square/{number}", 10)
                .retrieve()
                .bodyToMono(Response.class)
                .block();//bad usage to block
        System.out.println(response);
    }
    @Test
    void stepVerifierTest() {
        Mono<Response> response = webClient
                .get()
                .uri("reactive-math/square/{number}",
                        10)
                .retrieve()
                .bodyToMono(Response.class);
        StepVerifier.create(response)
                .expectNextMatches(r-> r.getOutput()==100)
                .verifyComplete();
    }
}
