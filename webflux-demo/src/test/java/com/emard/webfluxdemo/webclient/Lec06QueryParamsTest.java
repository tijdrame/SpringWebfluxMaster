package com.emard.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec06QueryParamsTest extends BaseTest {
    @Test
    void queryParamsTest(){
        String url = "http://localhost:8080/jobs/search?count={count}&page={page}";
        //URI uri =  UriComponentsBuilder.fromUriString(url)
          //      .build(10,20);
        var map = Map.of(
                "count", 10,
                "page", 20);
        var response = webClient.get()
                //.uri(uri)
                .uri(b-> b.path("jobs/search")
                        .query("count={count}&page={page}").build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

}
