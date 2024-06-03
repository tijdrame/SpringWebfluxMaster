package com.emard.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class Lec09AssignementTest extends BaseTest {
    private static final String FORMAT = "%d %s %d = %s";
    private static final int A = 10;

    @Test
    public void testCalculator(){
        var flux = Flux.range(1, 5)
                .flatMap(second-> Flux.just("+", "-","*","/")
                        .flatMap(op->send(second, op)))
                .doOnNext(System.out::println);
        StepVerifier.create(flux)
                .expectNextCount(20)
                .verifyComplete();
    }
    private Mono<String> send(int second, String op){
        return webClient.get()
                .uri("calculator/{first}/{second}", A, second)
                .headers(h->h.set("OP", op))
                .retrieve()
                .bodyToMono(String.class)
                .map(v->String.format(FORMAT, A, op, second,v));
    }
}
